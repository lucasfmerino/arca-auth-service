package com.arca.auth_service.modules.user.domain.dto;

import com.arca.auth_service.modules.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRegistrationDto(
        @NotBlank(message = "The username field is mandatory!")
        String username,

        @NotBlank(message = "The e-mail field is mandatory!")
        @Email(message = "This e-mail is invalid!")
        String email,

        @NotBlank(message = "The password field is mandatory!")
        @Size(min = 6, max = 20, message = "The password must be a minimum of 6 characters and a maximum of 20 characters")
        String password,

        @NotBlank(message = "The password confirmation field is mandatory!")
        @Size(min = 6, max = 20, message = "The password confirmation must be a minimum of 6 characters and a maximum of 20 characters")
        String passwordConfirmation,

        @NotNull
        List<Role> roles
)
{
}
