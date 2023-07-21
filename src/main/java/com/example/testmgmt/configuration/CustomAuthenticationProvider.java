package com.example.testmgmt.configuration;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.service.user.UserService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserService userService;

    public CustomAuthenticationProvider(UserService userService,
                                        UserDetailsService userDetailsService,
                                        PasswordEncoder passwordEncoder) {
        super();
        this.userService = userService;
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.findByEmail(authentication.getName());

        if (user != null) {

            try {

                String serverGenerateCode = OTP.create(user.getSecret(), OTP.timeInHex(), 6, Type.TOTP);

                // Get the object which have extra info that user detail provided when
                // they tried to log in
                CustomAuthenticationDetails customAuthenticationDetails = (CustomAuthenticationDetails) authentication.getDetails();

                logger.info("Server code: " + serverGenerateCode);
                logger.info("User code: " + customAuthenticationDetails.getUser2FaCode());

                if (!serverGenerateCode.equals(customAuthenticationDetails.getUser2FaCode())) {
                    throw new BadCredentialsException("User's 2FA code doesn't match with the server code");
                }

            } catch (Exception e) {
                logger.error("Oh no", e);
                throw new AuthenticationServiceException("Failed to generate server-side 2FA code");
            }

        }

        return super.authenticate(authentication);
    }
}
