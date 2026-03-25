package com.webApp.bloggingapp.services;

import com.webApp.bloggingapp.payloads.PaginationResponse;
import com.webApp.bloggingapp.payloads.PostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all posts
    PaginationResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    //get single Post
    PostDto getPost(Integer postId);

    //get all posts by category
    PaginationResponse getAllPostByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    //get all posts by User
    PaginationResponse getAllPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    //search Post
    PaginationResponse searchPosts(String keyword, Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    PostDto uploadPostImage(Integer postId, MultipartFile file) throws IOException;

}
