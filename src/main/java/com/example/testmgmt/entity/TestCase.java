package com.example.testmgmt.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String step;
    private String expectedResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String type;
    private String priority;
    private String estimate;
    private String precondition;

    @ManyToOne
    @JoinColumn(name = "createdBy_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "testCase", cascade = CascadeType.REMOVE)
    private List<TestRunUser> testRunUsers;

    // Constructors
    public TestCase() {
    }
    public TestCase(TestCase existedTestCase){
        this.title = existedTestCase.title;
        this.step = existedTestCase.step;
        this.expectedResult = existedTestCase.expectedResult;
        this.updatedAt = existedTestCase.updatedAt;
        this.updatedBy = existedTestCase.updatedBy;
        this.type = existedTestCase.type;
        this.priority = existedTestCase.priority;
        this.estimate = existedTestCase.estimate;
        this.precondition = existedTestCase.precondition;
    }
    public TestCase(Long id, String title, String step, String expectedResult,
                    LocalDateTime createdAt, LocalDateTime updatedAt, User createdBy,
                    User updatedBy, String type, String priority, String estimate
                    , Section section,
                    String precondition) {
        this.id = id;
        this.title = title;
        this.step = step;
        this.expectedResult = expectedResult;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.section = section;
        this.type = type;
        this.priority = priority;
        this.estimate = estimate;
        this.precondition = precondition;
    }

    // Getters and Setters

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }


    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public List<TestRunUser> getTestRunUsers() {
        return testRunUsers;
    }

    public void setTestRunUsers(List<TestRunUser> testRunUsers) {
        this.testRunUsers = testRunUsers;
    }
}
