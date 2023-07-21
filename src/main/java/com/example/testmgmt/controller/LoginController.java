package com.example.testmgmt.controller;

import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.UserRepository;
import com.example.testmgmt.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;


@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    private final UserRepository userRepository;

    public LoginController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("user", new User());
        if (userService.getCurrentUser() == null)
            return "login";
        return "dashboard";
    }

    @GetMapping("/login_error")
    public String loginError(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/otp")
    public String showLoginOtp(Model model) {
        model.addAttribute("pageTitle", "Login OTP");
        return "login_otp";
    }

    @PostMapping("/otp")
    public String processLoginOtp(HttpServletRequest request, Model model) {
        String token = request.getParameter("otp");
        User user = userService.getToken(token);
        if (user != null) {
            userService.resetToken(user);
            user.setActive(true);
            userRepository.save(user);
            return "dashboard";
        }
       else {
           model.addAttribute("verifyError", true);
           return "login_otp";
        }
    }

}
