package com.webApp.bloggingapp.mappers;

import com.webApp.bloggingapp.entities.Category;
import com.webApp.bloggingapp.payloads.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);
    CategoryDto toDto(Category category);
    List<CategoryDto> toDtoList(List<Category> categoryList);
}
