package com.example.testmgmt.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private String status;
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "test_run_user_id")
    private TestRunUser testRunUser;

    @ManyToOne
    @JoinColumn(name = "assign_user_id")
    private User assignUser;

    // Constructor
    public Comment() {
    }

    public Comment(String comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    // Getter Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public TestRunUser getTestRunUser() {
        return testRunUser;
    }

    public void setTestRunUser(TestRunUser testRunUser) {
        this.testRunUser = testRunUser;
    }

    public User getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(User assignUser) {
        this.assignUser = assignUser;
    }
}