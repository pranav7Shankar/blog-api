package com.webApp.bloggingapp.payloads;

import lombok.Data;

@Data
public class CommentDto {
    private int commentId;
    private String content;
}
