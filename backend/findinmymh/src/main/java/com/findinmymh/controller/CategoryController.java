package com.findinmymh.controller;

import com.findinmymh.dto.APIResponse;
import com.findinmymh.dto.CategoryDto;
import com.findinmymh.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryName) {
        APIResponse<CategoryDto> response = categoryService.createCategory(categoryName);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<CategoryDto[]>> getAllCategory() {
        APIResponse<CategoryDto[]> response = categoryService.getAllCategories();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{oldName}")
    public ResponseEntity<APIResponse<CategoryDto>> updateCategory(@RequestBody CategoryDto newCategory, @PathVariable String oldName) {
        APIResponse<CategoryDto> response = categoryService.updateCategory(oldName, newCategory);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @DeleteMapping
    public ResponseEntity<APIResponse<CategoryDto>> deleteCategory(@RequestBody CategoryDto category) {
        APIResponse<CategoryDto> response = categoryService.deleteCategory(category);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
