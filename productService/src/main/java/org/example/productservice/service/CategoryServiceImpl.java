package org.example.productservice.service;

import org.example.productservice.dto.CategoryDTO;
import org.example.productservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        categoryRepository.createCategory(categoryDTO);
        return categoryDTO;
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        categoryRepository.updateCategory(id, categoryDTO);
        return categoryDTO;
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepository.deleteCategory(id);
    }
}
