package com.example.testmgmt.configuration;

import com.example.testmgmt.service.user.CustomUserDetailsService;
import com.example.testmgmt.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAuthenticationDetailsSource customAuthenticationDetailsSource;


    @Autowired
    public WebSecurityConfig(CustomAuthenticationDetailsSource customAuthenticationDetailsSource,
                             CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.customAuthenticationDetailsSource = customAuthenticationDetailsSource;
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().ignoringRequestMatchers("/cases/{projectId}/copy").and()
                .authorizeHttpRequests((requests) -> requests

                        .requestMatchers("/", "/suites/**", "/users/**", "/project/**",
                                "/cases/**", "/runs/**", "/tests/**", "/milestones/**")

                        .authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                                .loginPage("/login/")
                                .loginProcessingUrl("/login")
                                .usernameParameter("email")
                                .failureUrl("/login/login_error")
                                .permitAll()
//                                .authenticationDetailsSource(customAuthenticationDetailsSource)
                                .defaultSuccessUrl("/", true)
//                                .successHandler(successHandler)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login/")
//                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .permitAll());

        return http.build();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(@Autowired UserService userService,
//                                                            @Autowired UserDetailsService userDetailsService,
//                                                            @Autowired PasswordEncoder passwordEncoder) {
//        return new CustomAuthenticationProvider(userService, userDetailsService, passwordEncoder);
//    }
}
