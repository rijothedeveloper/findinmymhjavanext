package com.findinmymh.services.impl;

import com.findinmymh.dto.APIResponse;
import com.findinmymh.dto.CategoryDto;
import com.findinmymh.entities.Category;
import com.findinmymh.exceptions.CategoryNotFoundException;
import com.findinmymh.repository.CategoryRepository;
import com.findinmymh.services.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public APIResponse<CategoryDto> createCategory(CategoryDto category) {
        categoryRepository.save(new Category(category.name()));
        return new APIResponse<CategoryDto>(true, "Category created successfully", category, null);
    }

    @Override
    public APIResponse<CategoryDto[]> getAllCategories() {
        CategoryDto[] categories = categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryDto(category.getName()))
                .toArray(CategoryDto[]::new);
        return categories.length == 0
                ? new APIResponse<CategoryDto[]>(false, "No categories found", null, null)
                : new APIResponse<CategoryDto[]>(true, "Categories found", categories, null);
    }

    @Override
    public APIResponse<CategoryDto> updateCategory(String oldName, CategoryDto category) {
        Category savedCategory = categoryRepository.findByName(oldName)
                .orElseThrow(() -> new CategoryNotFoundException("Category found in database"));;
        savedCategory.setName(category.name());
        categoryRepository.save(savedCategory);
        return new APIResponse<>(true, "Category updated successfully", category, null);
    }

    @Override
    public APIResponse<CategoryDto> deleteCategory(CategoryDto category) {
        Category savedCategory = categoryRepository.findByName(category.name())
                .orElseThrow(() -> new CategoryNotFoundException("Category found in database"));;
        categoryRepository.delete(savedCategory);
        return new APIResponse<>(true, "Category deleted successfully", category, null);
    }
}
