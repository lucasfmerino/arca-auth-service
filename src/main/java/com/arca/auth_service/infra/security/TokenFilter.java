package com.arca.auth_service.infra.security;

import com.arca.auth_service.modules.auth.service.TokenService;
import com.arca.auth_service.modules.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;


    /*
     *  OVERRIDE INTERNAL FILTER
     *
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = getToken(request);  // IMPLEMENT GET TOKEN

        if(token != null)
        {
            String subject = tokenService.validateTokenSubject(token);
            UserDetails user = userRepository.findByUsername(subject);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    /*
     * GET AUTHORIZATION HEADER (TOKEN)
     *
     */
    private String getToken(HttpServletRequest request)
    {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null)
        {
            return authorizationHeader.replace("Bearer", "").trim();
        }

        return null;
    }
}
