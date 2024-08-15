package com.luv2code.MiniProject.service;

import com.luv2code.MiniProject.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(Long id);

    List<CategoryDto> getAllCategory();

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    void deleteCategory(Long categoryId);
}
