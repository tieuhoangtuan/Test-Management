package com.example.testmgmt.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    @OneToMany(mappedBy = "role")
    private List<ProjectUser> projectUser;

    // Constructors
    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    // Getter Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ProjectUser> getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(List<ProjectUser> projectUser) {
        this.projectUser = projectUser;
    }

}
