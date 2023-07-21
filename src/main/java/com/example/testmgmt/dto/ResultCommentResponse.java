package com.example.testmgmt.dto;

import com.example.testmgmt.entity.User;

public class ResultCommentResponse {
    private String tableName;
    private Long id;
    private String status;
    private String comment;
    private String elapsedTime;
    private String createdDate;
    private User userId;
    private User assignUserId;
    private Long testRunUserId;

    public ResultCommentResponse() {
    }

    public ResultCommentResponse(String tableName, Long id, String status, String comment, String elapsedTime, String createdDate, User userId, User assignUserId, Long testRunUserId) {
        this.tableName = tableName;
        this.id = id;
        this.status = status;
        this.comment = comment;
        this.elapsedTime = elapsedTime;
        this.createdDate = createdDate;
        this.userId = userId;
        this.assignUserId = assignUserId;
        this.testRunUserId = testRunUserId;
    }

    public String getTableName() {
        return tableName;
    }

    public ResultCommentResponse setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ResultCommentResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ResultCommentResponse setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ResultCommentResponse setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public ResultCommentResponse setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
        return this;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public ResultCommentResponse setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public User getUserId() {
        return userId;
    }

    public ResultCommentResponse setUserId(User userId) {
        this.userId = userId;
        return this;
    }

    public User getAssignUserId() {
        return assignUserId;
    }

    public ResultCommentResponse setAssignUserId(User assignUserId) {
        this.assignUserId = assignUserId;
        return this;
    }

    public Long getTestRunUserId() {
        return testRunUserId;
    }

    public ResultCommentResponse setTestRunUserId(Long testRunUserId) {
        this.testRunUserId = testRunUserId;
        return this;
    }
}