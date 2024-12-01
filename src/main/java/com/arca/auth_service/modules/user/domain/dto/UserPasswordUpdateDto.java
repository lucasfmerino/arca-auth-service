package com.arca.auth_service.modules.user.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserPasswordUpdateDto(
        @NotNull(message = "The id field is mandatory!")
        UUID id,

        @NotNull(message = "The old password field is mandatory!")
        String oldPassword,

        @NotBlank(message = "The password field is mandatory!")
        @Size(min = 6, max = 20, message = "The password must be a minimum of 6 characters and a maximum of 20 characters")
        String newPassword,

        @NotBlank(message = "The password confirmation field is mandatory!")
        @Size(min = 6, max = 20, message = "The password confirmation must be a minimum of 6 characters and a maximum of 20 characters")
        String passwordConfirmation
) {
}
