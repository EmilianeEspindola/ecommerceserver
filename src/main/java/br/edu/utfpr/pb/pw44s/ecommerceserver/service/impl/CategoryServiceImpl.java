package br.edu.utfpr.pb.pw44s.ecommerceserver.service.impl;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Category;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.CategoryRepository;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}