package com.pakie.ahslocker.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        handleRedirect(authentication, response, request);
    }

    private void handleRedirect(Authentication authentication, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_STUDENT")) {
                redirectStrategy.sendRedirect(request, response, "/student");
                return;
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                redirectStrategy.sendRedirect(request, response, "/admin");
                return;
            } else if (authority.getAuthority().equals("ROLE_PARENT")) {
                redirectStrategy.sendRedirect(request, response, "/parent");
                return;

            }

            // Default redirect if role is not recognized
            redirectStrategy.sendRedirect(request, response, "/default");
        }
    }
}
