package com.example.testmgmt.entity;


import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "milestone")
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private boolean isStart;

    private boolean isComplete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_milestone_id")
    private Milestone parentMilestone;
    @OneToMany(mappedBy = "parentMilestone", cascade = CascadeType.REMOVE)
    private List<Milestone> subMilestones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "milestone")
    private List<TestPlan> testPlan;

    @OneToMany(mappedBy = "milestone")
    private List<TestRun> testRuns;

    public Milestone() {
    }

    public Milestone(String name, String description, Date startDate,
                     Date endDate, boolean isStart, Project project) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isStart = isStart;
        this.project = project;
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


    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public List<TestPlan> getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(List<TestPlan> testPlan) {
        this.testPlan = testPlan;
    }

    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Milestone getParentMilestone() {
        return parentMilestone;
    }

    public void setParentMilestone(Milestone parentMilestone) {
        this.parentMilestone = parentMilestone;
    }

    public List<Milestone> getSubMilestones() {
        return subMilestones;
    }

    public void setSubMilestones(List<Milestone> subMilestones) {
        this.subMilestones = subMilestones;
    }

    public String getEndDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
        String endDateFormat = formatter.format(this.endDate);
        return endDateFormat;
    }

    public String getStartDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
        String startDateFormat = formatter.format(this.startDate);
        return startDateFormat;
    }

    public String getDateNow(){
        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
        String dateNow = formatter.format(new Date());
        return dateNow;
    }

    public Long getTotalCount(){
        Long totalCount = 0L;
        for(TestRun testRun: this.testRuns){
            totalCount += testRun.getTestRunUsers().size();
        }
        return totalCount;
    }

    public Long getPassedCount(){
        Long passedCount = 0L;
        for(TestRun testRun: this.testRuns){
            for(TestRunUser testRunUser: testRun.getTestRunUsers()){
                System.out.println(testRunUser.getStatus());
                if (testRunUser.getStatus().equals("Passed")) {
                    passedCount = passedCount +1;
                }
            }
        }

        return passedCount;
    }

    public double getMilePassedPercentage() {
        Long total = this.getTotalCount();
        Long passed = this.getPassedCount();
        if(this.getTotalCount() == 0) {
            return 0.0;
        }
        System.out.println(total);
        System.out.println(passed);
        double percentage = ((double)passed / total) * 100;
        System.out.println(percentage);
        double roundedPercentage = Math.round(percentage * 10) / 10.0;
        return roundedPercentage;
    }

    @PreRemove
    private void preRemove() {
        if (subMilestones != null) {
            subMilestones.forEach(child -> child.setParentMilestone(null));
            testRuns.forEach(child -> child.setMilestone(null));
        }
    }
}
