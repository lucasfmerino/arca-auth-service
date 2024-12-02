package com.arca.auth_service.modules.auth.service;

import com.arca.auth_service.modules.role.Role;
import com.arca.auth_service.modules.user.domain.model.User;
import com.arca.auth_service.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SetupService {
    @Autowired
    private UserRepository userRepository;

    @Value("${api.security.role.password}")
    private String rolePassword;

    @Value("${api.security.role.username}")
    private String roleUsername;


    @Transactional
    public String start()
    {
        Optional<User> superAdminOptional = userRepository.findUserByUsername(roleUsername);

        if (superAdminOptional.isPresent())
        {
            User superAdmin = superAdminOptional.get();

            userRepository.delete(superAdmin);
            userRepository.flush();
        }

        createSuperAdmin();

        return "Status: ON";
    }


    private void createSuperAdmin()
    {
        String encryptedPassword = new BCryptPasswordEncoder().encode(rolePassword);
        User superAdmin = new User();

        superAdmin.setUsername(roleUsername);
        superAdmin.setEmail("superadmin@arca.com");
        superAdmin.setPassword(encryptedPassword);
        superAdmin.setIsActive(true);
        superAdmin.setRoles(List.of(Role.WEB147, Role.WEB136, Role.WEB101));

        userRepository.save(superAdmin);
    }

}
