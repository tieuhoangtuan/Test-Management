package com.example.testmgmt.service.test_run;

import com.example.testmgmt.entity.TestRun;

import java.util.List;

public interface TestRunService {

    void createTestRun(TestRun testRun, Long milestoneId,
                       Long assignUserId, int request,
                       List<String> testCaseList, Long projectId);

    List<TestRun> getAllTestRunByProjectId(Long projectId);

    TestRun getTestRunByIdAndProjectId(Long projectId, Long id);

    void deleteTestRun(Long runId);

    List<TestRun> getAllTestRunByMilestoneId(Long milestoneId);

}
