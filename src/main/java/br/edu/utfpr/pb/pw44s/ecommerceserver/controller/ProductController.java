package br.edu.utfpr.pb.pw44s.ecommerceserver.controller;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.ProductDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.mapper.ProductMapper;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Product;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final IProductService productService;
    private final ProductMapper productMapper;

    public ProductController(IProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll().stream().map(productMapper::toDto).toList());
    }

    @GetMapping("page")
    public ResponseEntity<Page<ProductDTO>> findAllPaged(@RequestParam int page, @RequestParam int size,
                                                         @RequestParam(required = false) String order,
                                                         @RequestParam(required = false) Boolean asc) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page - 1, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageRequest).map(productMapper::toDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDto(product));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> findByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId).stream().map(productMapper::toDto).toList());
    }
}