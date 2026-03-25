package com.webApp.bloggingapp.controllers;


import com.webApp.bloggingapp.payloads.ApiResponse;
import com.webApp.bloggingapp.payloads.CommentDto;
import com.webApp.bloggingapp.repositories.CommentRepo;
import com.webApp.bloggingapp.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Comments", description = "Comment management endpoints")
public class CommentController {
    private final CommentService commentService;
    private final CommentRepo commentRepo;

    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create a comment on a post")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createdComment = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Delete a comment")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(
                new ApiResponse("Comment Deleted Successfully", true), HttpStatus.OK);
    }

    @PutMapping("/comments/{commentId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Update a comment")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer commentId, @RequestBody CommentDto commentDto){
        CommentDto updatedComment = this.commentService.updateComment(commentDto, commentId);
        return ResponseEntity.ok(updatedComment);

    }


}
