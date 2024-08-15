package com.luv2code.MiniProject.controller;

import com.luv2code.MiniProject.exception.ErrorDetail;
import com.luv2code.MiniProject.payload.CategoryDto;
import com.luv2code.MiniProject.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategory(){
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category has been deleted!");
    }
}
