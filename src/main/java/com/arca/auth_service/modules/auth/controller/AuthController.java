package com.arca.auth_service.modules.auth.controller;

import com.arca.auth_service.modules.auth.service.AuthService;
import com.arca.auth_service.modules.auth.service.TokenService;
import com.arca.auth_service.modules.user.domain.dto.UserDisplayDto;
import com.arca.auth_service.modules.user.domain.dto.UserLoginDto;
import com.arca.auth_service.modules.user.domain.dto.UserRegistrationDto;
import com.arca.auth_service.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;


    /*
     * REGISTER
     *
     */
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDisplayDto> register(
            @RequestBody @Valid UserRegistrationDto userRegistrationDto, UriComponentsBuilder uriBuilder
    )
    {
        UserDisplayDto newUserDisplayDto = userService.create(userRegistrationDto);

        URI uri = uriBuilder.path("api/users/{id}").buildAndExpand(newUserDisplayDto.id()).toUri();

        return ResponseEntity.created(uri).body(newUserDisplayDto);
    }


    /*
     * START
     *
     */
    @PostMapping("/start")
    @Transactional
    public ResponseEntity<?> start()
    {
        return ResponseEntity.ok(authService.start());
    }


    /*
     * LOGIN
     *
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto userLoginDto)
    {
        try
        {
            return ResponseEntity.ok(authService.login(userLoginDto));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
