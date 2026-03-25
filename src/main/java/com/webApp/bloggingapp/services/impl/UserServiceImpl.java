package com.webApp.bloggingapp.services.impl;

import com.webApp.bloggingapp.entities.Role;
import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.exceptions.ResourceNotFoundException;
import com.webApp.bloggingapp.mappers.UserMapper;
import com.webApp.bloggingapp.payloads.UserDto;
import com.webApp.bloggingapp.repositories.UserRepo;
import com.webApp.bloggingapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_USER);
        User savedUser = this.userRepo.save(user);
        return this.userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setRole(userDto.getRole());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser = this.userRepo.save(user);
        return this.userMapper.toDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
//        return users.stream().map(userMapper::toDto).toList();
        return this.userMapper.toDtoList(users);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        if (!this.userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User", "UserId", userId);
        }
        this.userRepo.deleteCommentsByUserId(userId);
        this.userRepo.deletePostsByUserId(userId);
        this.userRepo.deleteById(userId);

    }

}
