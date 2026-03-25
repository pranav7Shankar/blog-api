package com.webApp.bloggingapp.controllers;

import com.webApp.bloggingapp.payloads.ApiResponse;
import com.webApp.bloggingapp.payloads.CategoryDto;
import com.webApp.bloggingapp.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name="Category", description = "Category Management Endpoints")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdcategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdcategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer cid){

        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, cid);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cid){
        this.categoryService.deleteCategory(cid);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK
        );
    }

    @GetMapping("/")
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get a category")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer cid){
        return ResponseEntity.ok(this.categoryService.getCategory(cid));
    }

}
