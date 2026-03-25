package com.webApp.bloggingapp.mappers;

import com.webApp.bloggingapp.entities.Comment;
import com.webApp.bloggingapp.payloads.CommentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
}
