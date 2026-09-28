package br.edu.utfpr.pb.pw44s.ecommerceserver.controller;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.OrderItemRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.OrderRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.OrderResponseDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.mapper.OrderMapper;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.*;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.IAddressService;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.IOrderService;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final IOrderService orderService;
    private final IAddressService addressService;
    private final IProductService productService;
    private final OrderMapper orderMapper;

    public OrderController (IOrderService orderService, IAddressService addressService,
                            IProductService productService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.addressService = addressService;
        this.productService = productService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> save(@RequestBody @Valid OrderRequestDTO orderRequestDTO,
                                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // Usuário autenticado
        // Endereço informado do usuário autenticado
        Address address = addressService.findByIdAndUserId(orderRequestDTO.getAddressId(), user.getId());
        if (address == null) {
            return ResponseEntity.notFound().build(); // Se endereço não encontrado, pedido não criado.
        }

        // Se o endereço for encontrado, faz uma cópia
        OrderAddress orderAddress = OrderAddress.builder().street(address.getStreet()).number(address.getNumber())
                .complement(address.getComplement()).neighborhood(address.getNeighborhood()).city(address.getCity())
                .state(address.getState()).zipCode(address.getZipCode()).build();

        // Pedido a ser montado com informações controladas pelo servidor
        Order order = Order.builder().date(LocalDateTime.now()).user(user).address(orderAddress).build();

        // Adição de itens ao pedido
        for (OrderItemRequestDTO itemDTO : orderRequestDTO.getItems()) {
            Product product = productService.findById(itemDTO.getProductId());
            if (product == null) {
                return ResponseEntity.notFound().build(); // Se produto não encontrado, pedido não criado
            }
            // Se o produto for encontrado é possível criar o pedido
            OrderItem orderItem = OrderItem.builder().order(order).product(product).quantity(itemDTO.getQuantity())
                    .price(product.getPrice()).build();
            order.getItems().add(orderItem);
        }
        Order savedOrder = orderService.save(order); // Pedido salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.toDto(savedOrder)); // Retorna CREATED
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findUserOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // Usuário autenticado
        // Busca somente os pedidos desse usuário
        List<OrderResponseDTO> orders = orderService.findByUserId(user.getId()).stream()
                .map(orderMapper::toDto).toList();
        return ResponseEntity.ok(orders); // Retorna OK
    }

    @GetMapping("page")
    public ResponseEntity<Page<OrderResponseDTO>> findUserOrdersPaged(@RequestParam int page, @RequestParam int size,
                                                                      @RequestParam(required = false) String order,
                                                                      @RequestParam(required = false) Boolean asc,
                                                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // Usuário autenticado
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page - 1, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return ResponseEntity.ok(orderService.findByUserId(user.getId(), pageRequest).map(orderMapper::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id, Authentication authentication) {
        User user = (User) authentication.getPrincipal(); // Usuário autenticado
        Order order = orderService.findByIdAndUserId(id, user.getId()); // Pedido específico do usuário
        if (order == null) {
            return ResponseEntity.notFound().build(); // Se não encontrado, retorna 404 Not Found.
        }
        return ResponseEntity.ok(orderMapper.toDto(order)); // Se encontra, retorna OK.
    }
}