package com.example.ecommerceweb.services;

import com.example.ecommerceweb.dtos.CategoryDTO;
import com.example.ecommerceweb.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category createCategory(CategoryDTO category);
    Category updateCategory(Long id, CategoryDTO category);
    void deleteCategoryById(Long id);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
}
