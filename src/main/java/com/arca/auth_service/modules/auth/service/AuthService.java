package com.arca.auth_service.modules.auth.service;

import com.arca.auth_service.modules.auth.domain.TokenDto;
import com.arca.auth_service.modules.role.Role;
import com.arca.auth_service.modules.user.domain.dto.UserLoginDto;
import com.arca.auth_service.modules.user.domain.model.User;
import com.arca.auth_service.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
     * GENERATE AUTH TOKEN
     *
     */
    public UsernamePasswordAuthenticationToken generateAuthToken(UserLoginDto userLoginDto)
    {
        return new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());
    }

}
