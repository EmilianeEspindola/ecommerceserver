package br.edu.utfpr.pb.pw44s.ecommerceserver.controller;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.CategoryDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.mapper.CategoryMapper;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(ICategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(
                categoryService.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList()));
    }
}