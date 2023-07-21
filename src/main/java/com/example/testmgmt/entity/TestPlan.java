package com.example.testmgmt.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "test_plan")
public class TestPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToMany(mappedBy = "testPlan")
    private List<TestRun> testRun;

    public TestPlan() {
    }

    public TestPlan(String name, String description, Milestone milestone) {
        this.name = name;
        this.description = description;
        this.milestone = milestone;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public List<TestRun> getTestRun() {
        return testRun;
    }

    public void setTestRun(List<TestRun> testRun) {
        this.testRun = testRun;
    }


}
