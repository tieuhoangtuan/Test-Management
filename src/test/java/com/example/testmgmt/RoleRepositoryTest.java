package com.example.testmgmt;


import com.example.testmgmt.entity.Role;
import com.example.testmgmt.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureDataJpa
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testCreateNewRoles() {
        Role admin = new Role("ADMIN");
        Role member = new Role("MEMBER");

        roleRepository.saveAll(List.of(admin, member));
        assertThat(roleRepository.findAll().size(), equalTo(2));
    }

}
