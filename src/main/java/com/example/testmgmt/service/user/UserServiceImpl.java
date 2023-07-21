package com.example.testmgmt.service.user;

import com.amdelamar.jotp.OTP;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.UserRepository;
import com.example.testmgmt.utils.EmailService;
import com.example.testmgmt.utils.Helper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getCurrentUser() {
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(currentUser);
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public void registerUser(HttpServletRequest request, User user) throws MessagingException, UnsupportedEncodingException {
        String code = RandomString.make(45);
        String email = user.getEmail();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActive(false);
        user.setEnable(false);
        user.setToken(code);
        user.setSecret(OTP.randomBase32(20));
//        String activeLink = Helper.getSiteURL(request) + "/register/active?code=" + code;
//        System.out.println(activeLink);
//        EmailService.sendLinkRegister(email, activeLink);
        userRepository.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not found any user with email " + email);
        }
    }

    @Override
    public User getToken(String resetPassToken) {
        return userRepository.findByToken(resetPassToken);
    }

    @Override
    public void resetToken(User user) {
        user.setToken(null);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setToken(null);

        userRepository.save(user);
    }

    @Override
    public String generateOTP(User user) {
        try {
            int randomOTP = (int) (Math.random() * 9000) + 1000;
            String OTP = Integer.toString(randomOTP);
            user.setToken(OTP);
            userRepository.save(user);
            EmailService.sendVerifyToken(user.getEmail(), OTP);
            return "success";
        } catch (UnsupportedEncodingException | MessagingException ex) {
            System.out.println("Error while sending email");
            return "error";
        }
    }
}
