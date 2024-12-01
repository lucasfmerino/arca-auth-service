package com.arca.auth_service.modules.user.domain.dto;

import com.arca.auth_service.modules.role.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record UserUpdateDto(
        @NotNull(message = "The id field is mandatory!")
        UUID id,
        String username,
        String email,
        Boolean isActive,
        List<Role> roles
)
{
}
