package com.webApp.bloggingapp.services;

import com.webApp.bloggingapp.payloads.CommentDto;

public interface CommentService {
    public CommentDto createComment(CommentDto commentDto, Integer postId);

    public void deleteComment(Integer commentId);

    public CommentDto updateComment(CommentDto commentDto, Integer commentId);
}
