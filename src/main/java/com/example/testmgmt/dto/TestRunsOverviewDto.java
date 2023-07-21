package com.example.testmgmt.dto;

public class TestRunsOverviewDto {

    private Long id;

    private String name;

    private String user;

    private int totalCount;

    private int passedCount;

    public TestRunsOverviewDto() {
    }

    public TestRunsOverviewDto(Long id, String name, int totalCount,
                               int passedCount, String user) {
        this.id = id;
        this.name = name;
        this.totalCount = totalCount;
        this.passedCount = passedCount;
        this.user = user;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getPassedPercentage() {
        if (totalCount == 0) {
            return 0.0;
        }
        double percentage = (double) passedCount / totalCount * 100;
        double roundedPercentage = Math.round(percentage * 10) / 10.0;
        return roundedPercentage;
    }
}
