package com.example.testmgmt.dto;

import com.example.testmgmt.entity.TestCase;

import java.util.List;

public class TestCaseDto {

    private String title;
    private String type;
    private String priority;
    private String estimate;
    private String precondition;
    private String step;
    private String expectedResult;

    private List<TestCaseDto> testCaseDtoList;

    public TestCaseDto(List<TestCaseDto> testCaseDtoList) {
        this.testCaseDtoList = testCaseDtoList;
    }

    public TestCaseDto() {
    }

    public TestCaseDto(String title, String type, String priority,
                       String estimate, String precondition, String step,
                       String expectedResult) {
        this.title = title;
        this.type = type;
        this.priority = priority;
        this.estimate = estimate;
        this.precondition = precondition;
        this.step = step;
        this.expectedResult = expectedResult;
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

    public List<TestCaseDto> getTestCaseDtoList() {
        return testCaseDtoList;
    }

    public void setTestCaseDtoList(List<TestCaseDto> testCaseDtoList) {
        this.testCaseDtoList = testCaseDtoList;
    }
}
