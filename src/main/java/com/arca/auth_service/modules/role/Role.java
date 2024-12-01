package com.arca.auth_service.modules.role;

import lombok.Getter;

@Getter
public enum Role
{
    WEB101("101"),
    WEB136("136"),
    WEB147("147");

    private final String role;

    Role(String role)
    {
        this.role = role;
    }
}
