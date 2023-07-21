package com.example.testmgmt.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    private static JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public static void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("support@testmng.com", "Test Management Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello, </p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public static void sendLinkRegister(String email, String activeLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("support@testmng.com", "Test Management Support");
        helper.setTo(email);

        String subject = "Here's the link to active your account";

        String content = "<p>Hello, </p>"
                + "<p>Welcome to Test Management.</p>"
                + "<p>Click the link below to activate your account</p>"
                + "<p><a href=\"" + activeLink + "\">Active Account</a></p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public static void sendVerifyToken(String email, String token) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("support@testmng.com", "Test Management Support");
        helper.setTo(email);

        String subject = "Here's the token to verify your login";

        String content = "<p>Hello, </p>"
                + "<p>Welcome to Test Management.</p>"
                + "<p>Below is your Token to login</p>"
                + token;

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public static void sendPassword(String email, String password) throws MessagingException, UnsupportedEncodingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("support@testmng.com", "Test Management Support");
        helper.setTo(email);

        String subject = "Here's the password to login to Test Management";

        String content = "<p>Hello, </p>"
                + "<p>Welcome to Test Management.</p>"
                + "<p>Below is your Password to login</p>"
                + password;

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
