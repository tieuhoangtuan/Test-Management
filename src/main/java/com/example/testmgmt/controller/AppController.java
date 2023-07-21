package com.example.testmgmt.controller;

import com.example.testmgmt.entity.Milestone;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.service.milestone.MilestoneService;
import com.example.testmgmt.service.project.ProjectService;
import com.example.testmgmt.service.test_run.TestRunService;
import com.example.testmgmt.service.user.UserNotFoundException;
import com.example.testmgmt.service.user.UserService;
import com.example.testmgmt.utils.EmailService;
import com.example.testmgmt.utils.Helper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class AppController {

    private final UserService userService;
    private final ProjectService projectService;

    public AppController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/forgot_password")
    public String showForgotPassword(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            // Try to find user and set token value if exits user
            userService.updateResetPasswordToken(token, email);

            // Generate password link
            String resetPasswordLink = Helper.getSiteURL(request) + "/reset_password?token=" + token;
            System.out.println(resetPasswordLink);

            // Send mail to reset password
            EmailService.sendEmail(email, resetPasswordLink);

            // notify
            model.addAttribute("message", "We have sent a reset password link to your email. Please check!");

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email.");
        }
        return "forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getToken(token);
        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your password");
        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getToken(token);
        if (user == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);
            model.addAttribute("success", "You have successfully changed your password.");
        }
        return "message";
    }

    @GetMapping("/")
    public String home(Model model) {
        User user = userService.getCurrentUser();
        Long userId = user.getId();
        model.addAttribute("projectList", projectService.findProjectByUserId(userId));
        return "dashboard";
    }

    @GetMapping("/otp_error")
    public String otpError() {
        return "otp_error";
    }

}
