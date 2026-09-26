package br.edu.utfpr.pb.pw44s.ecommerceserver.service;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
}