package com.arca.auth_service.modules.user.repository;

import com.arca.auth_service.modules.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>
{

}
