package com.example.testmgmt.service.test_run_user;
import com.example.testmgmt.data_container.StatusCounts;
import com.example.testmgmt.dto.TestRunsOverviewDto;
import com.example.testmgmt.entity.TestRun;
import com.example.testmgmt.entity.TestRunUser;

import java.util.List;

public interface TestRunUserService {

    List<TestRunUser> getTestRunUsersByTestRunId(Long testRunId);

    StatusCounts getStatusCounts(List<TestRunUser> testRunUsers);

    List<TestRunsOverviewDto> getDataToShowTestRunOverview(List<TestRun> testRuns);

}
