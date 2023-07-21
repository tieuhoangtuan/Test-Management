package com.example.testmgmt;


import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureDataJpa
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUsername("Test User");
        user.setPassword("$2a$12$yAI5w6bqs/.Y1ACVJvPneejqAz.lxTle48mzvKrA5.gmUXXJQiz7W");
        user.setEnable(true);
        user.setActive(true);
        userRepository.save(user);
    }
}