package com.webApp.bloggingapp.controllers;

import com.webApp.bloggingapp.config.AppConstants;
import com.webApp.bloggingapp.payloads.ApiResponse;
import com.webApp.bloggingapp.payloads.PaginationResponse;
import com.webApp.bloggingapp.payloads.PostDto;
import com.webApp.bloggingapp.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Tag(name = "Posts", description = "Post management endpoints")
public class PostController {

    private final PostService postService;

    //Constructor Injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create a post")
    public ResponseEntity<PostDto> createPost(
            @Valid
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) {
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //update Post
    @PutMapping("/posts/{postId}")
    @PreAuthorize("@postSecurity.isPostOwner(authentication, #postId) or hasRole('ADMIN')")
    @Operation(summary = "Update a post")
    public ResponseEntity<PostDto> updatePost(
            @Valid
            @RequestBody PostDto postDto,
            @PathVariable Integer postId
    ) {
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    @PreAuthorize("@postSecurity.isPostOwner(authentication, #postId) or hasRole('ADMIN')")
    @Operation(summary = "Delete a post")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    //get a single post
    @GetMapping("/posts/{postId}")
    @Operation(summary = "Get a post")
    public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
        PostDto post = this.postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    //get all posts
    @GetMapping("/posts")
    @Operation(summary = "Get all posts with pagination")
    public ResponseEntity<PaginationResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginationResponse response = this.postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }

    //get all posts by user
    @GetMapping("/user/{userId}/posts")
    @Operation(summary = "Get all posts of a user with pagination")
    public ResponseEntity<PaginationResponse> getPostsByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginationResponse response = this.postService.getAllPostByUser(userId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }

    //get all posts by category
    @GetMapping("/category/{categoryId}/posts")
    @Operation(summary = "Get all posts of a category with pagination")
    public ResponseEntity<PaginationResponse> getPostsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginationResponse response = this.postService.getAllPostByCategory(categoryId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }

    //search posts with keyword
    @GetMapping("/posts/search/{keyword}")
    @Operation(summary = "Get all posts containing a keyword")
    public ResponseEntity<PaginationResponse> searchPostsByKeyword(
            @PathVariable String keyword,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PaginationResponse response = this.postService.searchPosts(keyword, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }

    //Upload Post Image
    @PostMapping("/posts/{postId}/image/upload")
    @PreAuthorize("@postSecurity.isPostOwner(authentication, #postId) or hasRole('ADMIN')")
    @Operation(summary = "Upload an image for a post")
    public ResponseEntity<PostDto> uploadPostImage(
            @PathVariable Integer postId,
            @RequestParam("image") MultipartFile image
    ) throws IOException{
        PostDto postDto = this.postService.uploadPostImage(postId,image);
        return ResponseEntity.ok(postDto);
    }
}
