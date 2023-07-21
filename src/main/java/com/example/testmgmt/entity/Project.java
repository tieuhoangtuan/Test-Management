package com.example.testmgmt.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String status;

    @OneToMany(mappedBy = "project")
    private List<Section> sectionList;

    @OneToMany(mappedBy = "project")
    private List<ProjectUser> projectUser;

    @OneToMany(mappedBy = "project")
    private List<Milestone> milestones;

    @OneToMany(mappedBy = "project")
    private List<TestRun> testRuns;

    // Constructors
    public Project() {
    }

    public Project(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<ProjectUser> getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(List<ProjectUser> projectUser) {
        this.projectUser = projectUser;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }
}
