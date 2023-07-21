package com.example.testmgmt.dto;

import com.example.testmgmt.entity.TestRun;

public class TestResultDto {
    private String status;
    private String elapsedTime;
    private TestRun testRun;
    private String comment;
    private Long assignUser;
    private Long testRunUserId;

    public String getStatus() {
        return status;
    }

    public TestResultDto(String status, String elapsedTime, TestRun testRun, String comment, Long assignUser, Long testRunUserId) {
        this.status = status;
        this.elapsedTime = elapsedTime;
        this.testRun = testRun;
        this.comment = comment;
        this.assignUser = assignUser;
        this.testRunUserId = testRunUserId;
    }

    public TestResultDto() {
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(Long assignUser) {
        this.assignUser = assignUser;
    }

    public Long getTestRunUserId() {
        return testRunUserId;
    }

    public void setTestRunUserId(Long testRunUserId) {
        this.testRunUserId = testRunUserId;
    }
}
