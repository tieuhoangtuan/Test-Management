package com.example.testmgmt.service.user;

import com.example.testmgmt.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public record CustomUserDetailsService(UserService userService) implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("UserDto not found");
        }
        return new CustomUserDetails(user);
    }

}
