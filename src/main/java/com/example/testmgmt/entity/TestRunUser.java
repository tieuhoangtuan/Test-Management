package com.example.testmgmt.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "test_run_user")
public class TestRunUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
//    @ManyToOne
//    @JoinColumn(name = "assign_to_user_id")
//    private User assignToUser;

//    @ManyToOne
//    @JoinColumn(name = "test_run_id")
//    private TestRun testRun;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "assign_to_user_id")
    private User assignToUser;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "test_run_id")
    private TestRun testRun;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;

    @OneToMany(mappedBy = "testRunUser", cascade = CascadeType.ALL)
    private List<TestResult> testResult;

    @OneToMany(mappedBy = "testRunUser")
    private List<TestsHistory> testsHistories;

    @OneToMany(mappedBy = "testRunUser", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public TestRunUser() {
    }

    public TestRunUser(User assignToUser, TestRun testRun, TestCase testCase, String status) {
        this.assignToUser = assignToUser;
        this.testRun = testRun;
        this.testCase = testCase;
        this.status = status;
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

    public User getAssignToUser() {
        return assignToUser;
    }

    public void setAssignToUser(User assignToUser) {
        this.assignToUser = assignToUser;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public List<TestResult> getTestResult() {
        return testResult;
    }

    public void setTestResult(List<TestResult> testResult) {
        this.testResult = testResult;
    }

    public List<TestsHistory> getTestsHistories() {
        return testsHistories;
    }

    public void setTestsHistories(List<TestsHistory> testsHistories) {
        this.testsHistories = testsHistories;
    }


    //    public String getLatestStatus() {
//        if (testResult == null)
//            return "Untested";
//        TestResult latestResult = testResult.stream()
//                .max(Comparator.comparing(TestResult::getCreatedDate))
//                .orElse(null);
//        String status = latestResult.getStatus();
//        return status;
//    }
}
