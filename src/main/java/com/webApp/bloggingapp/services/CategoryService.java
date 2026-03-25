package com.webApp.bloggingapp.services;


import com.webApp.bloggingapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //delete
    void deleteCategory(Integer categoryId);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //get
    CategoryDto getCategory(Integer categoryId);

    //getAll
    List<CategoryDto> getAllCategories();


}
