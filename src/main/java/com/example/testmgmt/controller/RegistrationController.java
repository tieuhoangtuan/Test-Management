package com.example.testmgmt.controller;


import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.UserRepository;
import com.example.testmgmt.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final UserRepository userRepository;

    public RegistrationController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") @NotNull User user, HttpServletRequest request, Model model, RedirectAttributes redirect) throws MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        User checkUser = userService.findByEmail(email);
        if (checkUser != null) {
            model.addAttribute("registerError", true);
            return new ModelAndView("register");
        }
        else {
            userService.registerUser(request, user);
            redirect.addFlashAttribute("user", user);
            model.addAttribute("registerSuccess", true);
            return new ModelAndView("redirect:/registered");
        }
    }

    @GetMapping("registered")
    public String showRegisteredPage(Model model) throws UnsupportedEncodingException{
        User user = (User) (model.asMap().get("user"));

        // check if user was sent from register page
        if (user == null)
            // prevent people browsing straight to registered page
            return "redirect:/register";

        // gen OTP
        String otpUrl = OTP.getURL(user.getSecret(), 6, Type.TOTP, "spring-boot-testmgmt", user.getEmail());

        String twoFaQrUrl = String.format(
                "https://chart.googleapis.com/chart?cht=qr&chs=200x200&chl=%s",
                URLEncoder.encode(otpUrl, "UTF-8"));

        model.addAttribute("user", user);
        model.addAttribute("twoFaQrUrl", twoFaQrUrl);

        return "registered";
    }

    @GetMapping("/register/active")
    public String processActive(@Param(value = "code") String code, Model model) {
        User user = userService.getToken(code);
        if (user != null) {
            user.setEnable(true);
            user.setToken(null);
            userRepository.save(user);
            model.addAttribute("success", "Successful activation");
        }
        else {
            model.addAttribute("message", "Invalid Token");
        }
        return "message";
    }

    @GetMapping("/register_error")
    public String registerError(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("registerError", true);
        return "login";
    }

}
