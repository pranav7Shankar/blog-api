package com.webApp.bloggingapp.mappers;

import com.webApp.bloggingapp.entities.Post;
import com.webApp.bloggingapp.payloads.PostDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);
    Post toEntity(PostDto postDto);
    List<PostDto> toDtoList(List<Post> posts);
}
