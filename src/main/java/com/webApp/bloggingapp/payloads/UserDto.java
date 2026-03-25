package com.webApp.bloggingapp.payloads;

import com.webApp.bloggingapp.entities.Role;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class UserDto {
    private int userId;
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters !!")
    private String name;
    @Email(message = "Email Address is not valid")
    private String email;
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be between 3-10 chars !!")
    private String password;
    @NotBlank
    private String about;
    private Role role;

}
