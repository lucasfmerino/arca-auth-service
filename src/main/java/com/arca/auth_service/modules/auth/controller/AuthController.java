package com.arca.auth_service.modules.auth.controller;

import com.arca.auth_service.modules.auth.domain.TokenDto;
import com.arca.auth_service.modules.auth.service.AuthService;
import com.arca.auth_service.modules.auth.service.SetupService;
import com.arca.auth_service.modules.auth.service.TokenService;
import com.arca.auth_service.modules.user.domain.dto.UserDisplayDto;
import com.arca.auth_service.modules.user.domain.dto.UserLoginDto;
import com.arca.auth_service.modules.user.domain.dto.UserRegistrationDto;
import com.arca.auth_service.modules.user.domain.model.User;
import com.arca.auth_service.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SetupService setupService;

    @Autowired
    private AuthenticationManager authenticationManager;


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
        return ResponseEntity.ok(setupService.start());
    }


    /*
     * LOGIN
     *
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto userLoginDto)
    {
        UsernamePasswordAuthenticationToken authToken = authService.generateAuthToken(userLoginDto);

        try
        {
            Authentication authentication = authenticationManager.authenticate(authToken);
            String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
            return ResponseEntity.ok(new TokenDto(tokenJWT));
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
