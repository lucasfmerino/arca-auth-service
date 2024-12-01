package com.arca.auth_service.modules.auth.service;

import com.arca.auth_service.modules.auth.domain.TokenDto;
import com.arca.auth_service.modules.role.Role;
import com.arca.auth_service.modules.user.domain.dto.UserLoginDto;
import com.arca.auth_service.modules.user.domain.model.User;
import com.arca.auth_service.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService
{
    @Value("${api.security.role.password}")
    private String rolePassword;

    @Value("${api.security.role.username}")
    private String roleUsername;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;


    /*
     * LOAD USER
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }


    /*
     * START
     *
     */
    @Transactional
    public String start()
    {
        Optional<User> superAdminOptional = userRepository.findUserByUsername(roleUsername);

        if (superAdminOptional.isPresent()) {
            User superAdmin = superAdminOptional.get();

            userRepository.delete(superAdmin);
            userRepository.flush();
        }

        createSuperAdmin();

        return "Status: ON";
    }


    /*
     * CREATE SUPER ADMIN
     *
     */
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


    /*
     * LOGIN
     *
     */
    public TokenDto login(UserLoginDto userLoginDto)
    {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userLoginDto.username(),
                userLoginDto.password()
        );

        try
        {
            Authentication authentication = authenticationManager.authenticate(authToken);

            return new TokenDto(tokenService.generateToken((User) authentication.getPrincipal()));
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception.getMessage());
        }
    }


}
