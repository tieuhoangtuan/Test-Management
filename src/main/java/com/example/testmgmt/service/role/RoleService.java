package com.example.testmgmt.service.role;

import com.example.testmgmt.entity.Role;
import com.example.testmgmt.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }
}
