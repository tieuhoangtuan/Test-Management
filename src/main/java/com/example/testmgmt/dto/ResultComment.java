package com.example.testmgmt.dto;

import java.time.LocalDateTime;

public class ResultComment {
    private String table_name;
    private Long id;
    private String status;
    private String comment;
    private String elapsed_time;
    private LocalDateTime created_date;
    private Long user_id;
    private Long assign_user_Id;
    private Long test_run_user_id;

    public ResultComment() {
    }

    public ResultComment(String table_name, Long id, String status, String comment, String elapsed_time, LocalDateTime created_date, Long user_id, Long assign_user_Id, Long test_run_user_id) {
        this.table_name = table_name;
        this.id = id;
        this.status = status;
        this.comment = comment;
        this.elapsed_time = elapsed_time;
        this.created_date = created_date;
        this.user_id = user_id;
        this.assign_user_Id = assign_user_Id;
        this.test_run_user_id = test_run_user_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(String elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAssign_user_Id() {
        return assign_user_Id;
    }

    public void setAssign_user_Id(Long assign_user_Id) {
        this.assign_user_Id = assign_user_Id;
    }

    public Long getTest_run_user_id() {
        return test_run_user_id;
    }

    public void setTest_run_user_id(Long test_run_user_id) {
        this.test_run_user_id = test_run_user_id;
    }
}
