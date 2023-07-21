package com.example.testmgmt.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_section_id")
    private Section parentSection;

    @OneToMany(mappedBy = "parentSection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> subSectionList = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<TestCase> testCaseList;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    //Constructor
    public Section() {
    }

    public Section(Long id, String name, String description, Section parentSection, List<Section> subSectionList, List<TestCase> testCaseList, Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentSection = parentSection;
        this.subSectionList = subSectionList;
        this.testCaseList = testCaseList;
        this.project = project;
    }

    public Section(String name, String description, Section parentSection, Project project) {
        this.name = name;
        this.description = description;
        this.parentSection = parentSection;
        this.project = project;
    }

    //Getter & Setter

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

    public Section getParentSection() {
        return parentSection;
    }

    public void setParentSection(Section parentSection) {
        this.parentSection = parentSection;
    }

    public List<Section> getSubSectionList() {
        return subSectionList;
    }

    public void setSubSectionList(List<Section> subSectionList) {
        this.subSectionList = subSectionList;
    }

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public void setTestCaseList(List<TestCase> testCaseList) {
        this.testCaseList = testCaseList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
