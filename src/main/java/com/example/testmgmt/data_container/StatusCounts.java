package com.example.testmgmt.data_container;

public class StatusCounts {
    private int untestedCount = 0;
    private int passedCount;
    private int failedCount;
    private int blockedCount;
    private int retestCount;
    private int totalCount;

    public StatusCounts() {
    }

    public StatusCounts(int untestedCount, int passedCount,
                        int failedCount, int blockedCount,
                        int retestCount, int totalCount) {
        this.untestedCount = untestedCount;
        this.passedCount = passedCount;
        this.failedCount = failedCount;
        this.blockedCount = blockedCount;
        this.retestCount = retestCount;
        this.totalCount = totalCount;
    }

    public int getUntestedCount() {
        return untestedCount;
    }

    public void setUntestedCount(int untestedCount) {
        this.untestedCount = untestedCount;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(int blockedCount) {
        this.blockedCount = blockedCount;
    }

    public int getRetestCount() {
        return retestCount;
    }

    public void setRetestCount(int retestCount) {
        this.retestCount = retestCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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
