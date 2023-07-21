package com.example.testmgmt.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String elapsedTime;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "assign_user_id")
    private User assignUser;

    @ManyToOne
    @JoinColumn(name = "test_run_user_id")
    private TestRunUser testRunUser;


    // Constructors
    public TestResult() {
    }

    public TestResult(String status, LocalDateTime createdDate, String elapsedTime,
                      User user, String comment, User assignUser, TestRunUser testRunUser) {
        this.status = status;
        this.elapsedTime = elapsedTime;
        this.user = user;
        this.createdDate = createdDate;
        this.comment = comment;
        this.assignUser = assignUser;
        this.testRunUser = testRunUser;
    }

    // Getter Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public User getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(User assignUser) {
        this.assignUser = assignUser;
    }

    public TestRunUser getTestRunUser() {
        return testRunUser;
    }
    
    public void setTestRunUser(TestRunUser testRunUser) {
        this.testRunUser = testRunUser;
    }
}
