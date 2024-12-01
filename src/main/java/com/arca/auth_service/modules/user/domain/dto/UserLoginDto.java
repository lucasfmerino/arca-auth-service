package com.arca.auth_service.modules.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
        @NotBlank(message = "The username field is mandatory!")
        String username,

        @NotBlank(message = "The password field is mandatory!")
        @Size(min = 6, max = 20, message = "The password must be a minimum of 6 characters and a maximum of 20 characters")
        String password
)
{
}
