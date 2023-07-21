package com.example.testmgmt.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomAuthenticationDetails extends WebAuthenticationDetails {

    private final String user2FaCode;

    // store 2fa code in field and access them in Authentication Provider
    public CustomAuthenticationDetails(HttpServletRequest request) {
        super(request);
        System.out.println(request.getRequestURI() + "::" + request.getParameter("2facode"));
        this.user2FaCode = request.getParameter("2facode");
    }

    public String getUser2FaCode() {
        return user2FaCode;
    }
}
