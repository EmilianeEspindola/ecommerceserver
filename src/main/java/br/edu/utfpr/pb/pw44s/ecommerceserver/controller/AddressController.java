package br.edu.utfpr.pb.pw44s.ecommerceserver.controller;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.AddressDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.mapper.AddressMapper;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Address;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.User;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController {
    private final IAddressService addressService;
    private final AddressMapper addressMapper;

    public AddressController(IAddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @PostMapping
    public ResponseEntity<AddressDTO> save(@RequestBody @Valid AddressDTO addressDTO, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Address address = addressMapper.toEntity(addressDTO);
        address.setUser(user); // Salva o endereço para o usuário autenticado
        Address savedAddress = addressService.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressMapper.toDto(savedAddress));
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findUserAddresses(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        // Busca todos os endereços do usuário logado
        List<AddressDTO> addresses = addressService.findByUserId(user.getId()) // Lista os endereços
                .stream().map(addressMapper::toDto).toList();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id,
                                             @RequestBody @Valid AddressDTO addressDTO, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Address address = addressService.findByIdAndUserId(id, user.getId()); // Pega um endereço específico
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());

        Address updatedAddress = addressService.save(address);
        return ResponseEntity.ok(addressMapper.toDto(updatedAddress));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Address address = addressService.findByIdAndUserId(id, user.getId());
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Retorna o usuário autenticado
    private User getAuthenticatedUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}