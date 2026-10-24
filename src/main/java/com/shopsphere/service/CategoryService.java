
package com.shopsphere.service;

import com.shopsphere.entity.Category;
import com.shopsphere.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Category not found: " + id));
    }

    public Category updateCategory(
            Long id, Category updatedCategory) {

        Category category = getCategoryById(id);
        category.setName(updatedCategory.getName());
        category.setDescription(
                updatedCategory.getDescription());
        category.setImageUrl(updatedCategory.getImageUrl());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
