package com.assessme.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author: monil
 * Created on: 2020-06-03
 */
@Configuration
public class AuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println("roles after login: " + roles);
        if (roles.contains("ADMIN")) {
            response.sendRedirect("/course_admin");
        } else if (roles.contains("INSTRUCTOR")) {
            response.sendRedirect("/home");
        } else if (roles.contains("TA")) {
            response.sendRedirect("/home");
        } else if (roles.contains("GUEST") || roles.contains("STUDENT")) {
            response.sendRedirect("/course_info");
        } else {
            response.sendRedirect("/home");
        }

    }
}
