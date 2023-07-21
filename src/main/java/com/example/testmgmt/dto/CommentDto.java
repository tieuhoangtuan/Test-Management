package com.example.testmgmt.dto;

public class CommentDto {
    private String comment;
    private Long AssignUserId;

    public CommentDto() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAssignUserId() {
        return AssignUserId;
    }

    public void setAssignUserId(Long assignUserId) {
        AssignUserId = assignUserId;
    }
}
