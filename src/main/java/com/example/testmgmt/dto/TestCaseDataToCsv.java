package com.example.testmgmt.dto;

public class TestCaseDataToCsv {

    private Long id;
    private String title;
    private String type;
    private String priority;
    private String estimate;
    private String precondition;
    private String step;
    private String expectedResult;
    private String createdBy;
    private String section;

    public TestCaseDataToCsv() {
    }

    public TestCaseDataToCsv(Long id, String title, String type, String priority, String estimate, String precondition
            , String step, String expectedResult, String createdBy, String section) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.priority = priority;
        this.estimate = estimate;
        this.precondition = precondition;
        this.step = step;
        this.expectedResult = expectedResult;
        this.createdBy = createdBy;
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getPrecondition() {
        return precondition;
    }

    public void setPrecondition(String precondition) {
        this.precondition = precondition;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
