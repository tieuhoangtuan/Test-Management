package com.example.testmgmt.entity;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tests_history")
public class TestsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private String action;

    @Temporal(TemporalType.DATE)
    private Date actionDate;

    @ManyToOne
    @JoinColumn(name = "test_run_user")
    private TestRunUser testRunUser;

    public TestsHistory() {
    }

    public TestsHistory(String status, String action, Date actionDate,
                        TestRunUser testRunUser) {
        this.status = status;
        this.action = action;
        this.actionDate = actionDate;
        this.testRunUser = testRunUser;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public TestRunUser getTestRunUser() {
        return testRunUser;
    }

    public void setTestRunUser(TestRunUser testRunUser) {
        this.testRunUser = testRunUser;
    }
}
