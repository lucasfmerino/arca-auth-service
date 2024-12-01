package com.arca.auth_service.modules.user.repository;

import com.arca.auth_service.modules.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>
{
    /*
     *  FIND USER BY USERNAME
     *
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    UserDetails findByUsername(@Param("username") String username);


    /*
     *  FIND USER BY USERNAME (OPTIONAL)
     *
     */
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);


    /*
     *  FIND BY EMAIL
     *
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
