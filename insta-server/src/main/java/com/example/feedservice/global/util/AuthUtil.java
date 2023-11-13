package com.example.feedservice.global.util;

import com.example.feedservice.domain.user.User;
import com.example.feedservice.domain.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final UserRepository userRepository;

    public Long getLoginUserIndex() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Long userId = ((User) principal).getId();
        return userId;
    }

    public Optional<User> getLoginUser() {
        return userRepository.findById(getLoginUserIndex());
    }
}
