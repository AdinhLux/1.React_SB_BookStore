package com.weCode.bookStore.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * We are going to intercept HTTP requests.
 * It will look for JWT inside the header, extract the daa and verify with the secret key.
 * <p>
 * If valid we allow access to the user.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenWithBearer = request.getHeader("Authorization");

        if (tokenWithBearer == null || !tokenWithBearer.startsWith("Bearer")) {
            // We are not going to validate our request
            filterChain.doFilter(request, response);
            return;
        }

        // Let's extract token from bearer
        String token = tokenWithBearer.substring(7);
        Authentication authentication = jwtUtil.validateToken(token);

        // Set security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
