package com.example.testmgmt.dto;

public class MilestoneDto {
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private boolean isComplete;
    private Long  projectId;

    private Long parentMilestoneId;

    public MilestoneDto() {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public Long getParentMilestoneId() {
        return parentMilestoneId;
    }

    public void setParentMilestoneId(Long parentMilestoneId) {
        this.parentMilestoneId = parentMilestoneId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
