package com.assessme.auth;

import com.assessme.model.User;
import com.assessme.service.UserService;
import com.assessme.service.UserServiceImpl;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Darshan Kathiriya
 * @created 13-June-2020 4:58 PM
 */
@Service
public class CurrentUserService {

    private static CurrentUserService instance;
    private final Logger logger = LoggerFactory.getLogger(CurrentUserService.class);
    private final UserService userService;

    public CurrentUserService() {
        this.userService = UserServiceImpl.getInstance();
    }

    public static CurrentUserService getInstance() {
        if (instance == null) {
            instance = new CurrentUserService();
        }
        return instance;
    }

    public Optional<User> getAuthenticatedUser() throws Exception {
        Optional<User> user = Optional.empty();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                String userEmail = ((org.springframework.security.core.userdetails.User) authentication
                    .getPrincipal()).getUsername();

                user = Optional.of(userService.getUserFromEmail(userEmail).get());
            }
        } catch (Exception e) {
            throw e;
        }
        return user;
    }

    public boolean isInstructor() throws Exception {
        User user = getAuthenticatedUser().get();

        Set<String> userRoles = userService.getUserWithRolesFromEmail(user.getEmail()).get()
            .getUserRoles();
        logger.info(Arrays.toString(userRoles.toArray()));
        return userRoles.contains("INSTRUCTOR");
    }
}
