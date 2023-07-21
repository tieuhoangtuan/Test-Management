package com.example.testmgmt.configuration;

import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.UserRepository;
import com.example.testmgmt.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private UserService userService;

    private UserRepository userRepository;

    public CustomLogoutSuccessHandler(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String email = authentication.getName();
//        User user = userService.findByEmail(email);
//        user.setActive(false);
//        userRepository.save(user);
        String redirectUrl="/login/";
        new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);
//        super.onLogoutSuccess(request, response, authentication);
    }
}
