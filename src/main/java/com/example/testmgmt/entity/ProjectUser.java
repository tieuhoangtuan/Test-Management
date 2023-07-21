package com.example.testmgmt.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "project_user")
public class ProjectUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id")
    private Role role;


    // Constructors
    public ProjectUser() {
    }

    public ProjectUser(User user) {
        this.user = user;
    }

    public ProjectUser(User user, Project project, Role role) {
        this.user = user;
        this.project = project;
        this.role = role;
    }

    public ProjectUser(User user, Project project) {
        this.user = user;
        this.project = project;
    }

    //Getter Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
