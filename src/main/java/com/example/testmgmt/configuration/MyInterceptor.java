package com.example.testmgmt.configuration;

import com.example.testmgmt.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.example.testmgmt.service.user.UserService;


@Component
public class MyInterceptor implements HandlerInterceptor {

    private final UserService userService;


    @Autowired
    public MyInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI().toString();
        if(requestUrl.startsWith("/login") || requestUrl.startsWith("/register") || requestUrl.startsWith("/otp_error")
                || requestUrl.contains("/forgot_password") || requestUrl.contains("/reset_password") || requestUrl.startsWith("/css/")){
            return true;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        System.out.println(email);
        User user = userService.findByEmail(email);
        if (user.isActive()) {
            return true;
        }
        else {
            String redirectUrl = "/login/otp";
            new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
        }
        return true;
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
