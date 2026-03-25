package com.webApp.bloggingapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private int postId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String imageUrl;
    private Date dateAdded;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments;
}
