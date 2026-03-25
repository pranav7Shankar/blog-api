package com.webApp.bloggingapp.services.impl;

import com.webApp.bloggingapp.config.AppConstants;
import com.webApp.bloggingapp.entities.Category;
import com.webApp.bloggingapp.entities.Post;
import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.exceptions.ResourceNotFoundException;
import com.webApp.bloggingapp.mappers.PostMapper;
import com.webApp.bloggingapp.payloads.PaginationResponse;
import com.webApp.bloggingapp.payloads.PostDto;
import com.webApp.bloggingapp.repositories.CategoryRepo;
import com.webApp.bloggingapp.repositories.PostRepo;
import com.webApp.bloggingapp.repositories.UserRepo;
import com.webApp.bloggingapp.services.FileService;
import com.webApp.bloggingapp.services.PostService;
import com.webApp.bloggingapp.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;

    private final UserRepo userRepo;

    private final CategoryRepo categoryRepo;

    private final PostMapper postMapper;

    private final PaginationUtils paginationUtils;

    private final FileService fileService;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = this.postMapper.toEntity(postDto);
        post.setImageUrl(AppConstants.DEFAULT_IMAGE_URL);
        post.setDateAdded(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.postMapper.toDto(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        post.setImageUrl(postDto.getImageUrl());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Post updatedPost = this.postRepo.save(post);
        return this.postMapper.toDto(updatedPost);
    }

    @Override
    @Transactional
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        // Detach post from user's collection
        User user = post.getUser();
        if (user != null) {
            user.getPosts().remove(post);
        }

        // Detach post from category's collection
        Category category = post.getCategory();
        if (category != null) {
            category.getPosts().remove(post);
        }

        this.postRepo.delete(post);
    }

    @Override
    public PaginationResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize,sort);
        Page<Post> pagePosts = this.postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        return paginationUtils.buildResponse(pagePosts, this.postMapper.toDtoList(posts));
    }

    @Override
    public PostDto getPost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        return this.postMapper.toDto(post);
    }

    @Override
    public PaginationResponse getAllPostByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category_Id", categoryId));
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = this.postRepo.findByCategory(category, pageable);
        List<PostDto> postsDto =this.postMapper.toDtoList(page.getContent());
        return paginationUtils.buildResponse(page,postsDto);
    }

    @Override
    public PaginationResponse getAllPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = this.postRepo.findByUser(user, pageRequest);
        List<PostDto> posts = this.postMapper.toDtoList(page.getContent());
        return this.paginationUtils.buildResponse(page, posts);
    }

    @Override
    public PaginationResponse searchPosts(String keyword,Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = this.postRepo.findByTitleContaining(keyword, pageRequest);

        List<PostDto> postDtos = this.postMapper.toDtoList(page.getContent());
        return this.paginationUtils.buildResponse(page, postDtos);
    }

    @Override
    public PostDto uploadPostImage(Integer postId, MultipartFile file) throws IOException {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        String imageUrl = this.fileService.uploadImage(file);
        post.setImageUrl(imageUrl);
        Post updatedPost = this.postRepo.save(post);
        return this.postMapper.toDto(updatedPost);

    }
}
