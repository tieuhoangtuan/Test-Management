package com.example.testmgmt.service.user;

import com.example.testmgmt.entity.User;
import com.example.testmgmt.entity.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getCurrentUser();

    User findByEmail(String username);

    void registerUser(HttpServletRequest request, User user) throws MessagingException, UnsupportedEncodingException;

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    User getToken(String resetPassToken);

    void resetToken(User n);

    void updatePassword(User user, String newPassword);

    String generateOTP(User user);
}
