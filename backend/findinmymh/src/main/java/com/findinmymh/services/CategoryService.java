package com.findinmymh.services;

import com.findinmymh.dto.APIResponse;
import com.findinmymh.dto.CategoryDto;

public interface CategoryService {
    APIResponse<CategoryDto> createCategory(CategoryDto category);

    APIResponse<CategoryDto[]> getAllCategories();

    APIResponse<CategoryDto> updateCategory(String oldName, CategoryDto category);

    APIResponse<CategoryDto> deleteCategory(CategoryDto category);
}
