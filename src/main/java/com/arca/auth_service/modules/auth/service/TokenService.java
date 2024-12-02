package com.arca.auth_service.modules.auth.service;

import com.arca.auth_service.modules.role.Role;
import com.arca.auth_service.modules.user.domain.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService
{
    @Value("${api.security.token.secret}")
    private String secret;


    /*
     * GENERATE TOKEN
     *
     */
    public String generateToken(User user)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            List<String> roles = user.getRoles().stream()
                    .map(Role::name)
                    .toList();

            return JWT.create()
                    .withIssuer("arca-api")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId().toString())
                    .withClaim("roles", roles)
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        }
        catch (JWTCreationException exception)
        {
            throw new RuntimeException("Unable to generate token!", exception);
        }
    }


    /*
     * VALIDATE TOKEN (GET SUBJECT)
     *
     */
    public String validateTokenSubject(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("arca-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException exception)
        {
            throw new RuntimeException("Invalid or expired JWT Token!", exception);
        }
    }


    /*
     * VALIDATE TOKEN (GET ID)
     *
     */
    public String validateTokenClaimId(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("arca-api")
                    .build()
                    .verify(token)
                    .getClaim("id").asString();
        }
        catch (JWTVerificationException exception)
        {
            throw new RuntimeException("Invalid or expired JWT Token!", exception);
        }
    }


    /*
     * VALIDATE TOKEN (GET ROLES)
     *
     */
    public List<String> validateTokenClaimRoles(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("arca-api")
                    .build()
                    .verify(token)
                    .getClaim("roles").asList(String.class);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired JWT Token!", exception);
        }
    }


    /*
     * TOKEN EXPIRATION
     *
     */
    private Instant generateExpirationDate()
    {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }
}
