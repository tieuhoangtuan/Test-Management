package com.example.testmgmt;

import com.example.testmgmt.entity.Role;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.RoleRepository;
import com.example.testmgmt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RoleConfig {
    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role lead = new Role("Lead");
            Role noAccess = new Role("No Access");
            Role read = new Role("Read-only");
            Role tester = new Role("Tester");
            Role designer = new Role("Designer");
            List<Role> roles = Arrays.asList(lead, noAccess, read, tester, designer);
            roleRepository.saveAll(roles);

            User user = new User();
            user.setEmail("test@gmail.com");
            user.setUsername("Test User");
            user.setPassword("$2a$12$yAI5w6bqs/.Y1ACVJvPneejqAz.lxTle48mzvKrA5.gmUXXJQiz7W");
            user.setEnable(true);
            user.setActive(true);
            userRepository.save(user);
        };
    }
}
