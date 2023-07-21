package com.example.testmgmt.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String token;
    private boolean isActive;
    private boolean isEnable;
    private String secret;

    @OneToMany(mappedBy = "initiator")
    private List<TestRun> testRuns;

    @OneToMany(mappedBy = "assignToUser")
    private List<TestRunUser> testRunUsers;


    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<TestResult> testResults;

    @OneToMany(mappedBy = "assignUser")
    private List<TestResult> assignUserResults;

    @OneToMany(mappedBy = "user")
    private List<ProjectUser> projectUser;

    @OneToMany(mappedBy = "createdBy")
    private List<TestCase> testCase;

    // Constructor
    public User() {
    }


    public User(String username, String fullName, String password, String email,
                String token, boolean isActive, boolean isEnable, String secret) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.token = token;
        this.isActive = isActive;
        this.isEnable = isEnable;
        this.secret = secret;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public List<ProjectUser> getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(List<ProjectUser> projectUser) {
        this.projectUser = projectUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String resetPassToken) {
        this.token = resetPassToken;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public List<TestCase> getTestCase() {
        return testCase;
    }

    public void setTestCase(List<TestCase> testCase) {
        this.testCase = testCase;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<TestRunUser> getTestRunUsers() {
        return testRunUsers;
    }

    public void setTestRunUsers(List<TestRunUser> testRunUsers) {
        this.testRunUsers = testRunUsers;
    }

    public List<TestResult> getAssignUserResults() {
        return assignUserResults;
    }

    public void setAssignUserResults(List<TestResult> assignUserResults) {
        this.assignUserResults = assignUserResults;
    }
}
