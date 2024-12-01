package com.arca.auth_service.modules.user.domain.dto;

import com.arca.auth_service.modules.role.Role;
import com.arca.auth_service.modules.user.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDisplayDto(
        UUID id,
        String username,
        String email,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Role> roles
)
{
    public UserDisplayDto(User user)
    {
        this (
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getIsActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRoles()
        );
    }
}
