package com.example.testmgmt.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contentType;
    private Long size;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;

    @ManyToOne
    @JoinColumn(name = "test_run_id")
    private TestRun testRun;

    // Constructor
    public Attachment() {
    }

    public Attachment(String name, String contentType, Long size, byte[] data, TestCase testCase, TestRun testRun) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.data = data;
        this.testCase = testCase;
        this.testRun = testRun;
    }

    // Getter Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }
}
