package com.webApp.bloggingapp.services.impl;

import com.webApp.bloggingapp.entities.Category;
import com.webApp.bloggingapp.entities.Post;
import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.exceptions.DuplicateResourceException;
import com.webApp.bloggingapp.exceptions.ResourceNotFoundException;
import com.webApp.bloggingapp.mappers.CategoryMapper;
import com.webApp.bloggingapp.payloads.CategoryDto;
import com.webApp.bloggingapp.repositories.CategoryRepo;
import com.webApp.bloggingapp.repositories.PostRepo;
import com.webApp.bloggingapp.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    private final PostRepo postRepo;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        boolean exists = this.categoryRepo
                .findByCategoryTitleIgnoreCase(categoryDto.getCategoryTitle())
                .isPresent();
        if (exists) {
            throw new DuplicateResourceException("Category", "categoryTitle", categoryDto.getCategoryTitle());
        }
        Category category = this.categoryMapper.toEntity(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        // ✅ Step 1 — Detach category from all its posts
        List<Post> posts = category.getPosts();
        posts.forEach(post -> {
            // ✅ Step 2 — Detach post from user's collection
            User user = post.getUser();
            if (user != null) {
                user.getPosts().remove(post);
            }
            // ✅ Step 3 — Clear comments from post
            post.getComments().clear();
            // ✅ Step 4 — Detach category from post
            post.setCategory(null);
        });

        // ✅ Step 5 — Clear posts from category
        category.getPosts().clear();

        // ✅ Step 6 — Delete category — posts and comments deleted via CascadeType.ALL
        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);
        return this.categoryMapper.toDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        return this.categoryMapper.toDto(category);

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories= this.categoryRepo.findAll();
        return this.categoryMapper.toDtoList(categories);
    }
}
