package com.cat.controller;

import com.cat.model.dto.category.CategoryDTO;
import com.cat.model.dto.category.CreateCategoryDTO;
import com.cat.model.dao.Category;
import com.cat.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok().body(categoryService.getAllCategories(pageable)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(value = "id") UUID categoryId) {
        return ResponseEntity.ok().body(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<CreateCategoryDTO> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO) {
        return ResponseEntity.ok().body(categoryService.createCategory(createCategoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(value = "id") UUID categoryId,
                                                      @RequestBody CategoryDTO categoryDetails) {

        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") UUID categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}

