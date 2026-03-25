package com.webApp.bloggingapp.mappers;

import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.payloads.UserDto;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    List<UserDto> toDtoList(List<User> users);
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDto userDto);
}
