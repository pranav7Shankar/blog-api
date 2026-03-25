package com.webApp.bloggingapp.services.impl;

import com.webApp.bloggingapp.entities.Comment;
import com.webApp.bloggingapp.entities.Post;
import com.webApp.bloggingapp.exceptions.ResourceNotFoundException;
import com.webApp.bloggingapp.mappers.CommentMapper;
import com.webApp.bloggingapp.payloads.CommentDto;
import com.webApp.bloggingapp.payloads.PostDto;
import com.webApp.bloggingapp.repositories.CommentRepo;
import com.webApp.bloggingapp.repositories.PostRepo;
import com.webApp.bloggingapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostRepo postRepo;

    private final CommentRepo commentRepo;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        Comment comment = this.commentMapper.toEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.commentMapper.toDto(savedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        this.commentRepo.delete(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = this.commentRepo.save(comment);
        return this.commentMapper.toDto(updatedComment);
    }
}
