package com.example.testmgmt.service.test_run_user;
import com.example.testmgmt.data_container.StatusCounts;
import com.example.testmgmt.dto.TestRunsOverviewDto;
import com.example.testmgmt.entity.TestRun;
import com.example.testmgmt.entity.TestRunUser;
import com.example.testmgmt.repository.TestRunUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestRunUserServiceImpl implements TestRunUserService{

    private TestRunUserRepository testRunUserRepository;

    @Autowired
    public TestRunUserServiceImpl(TestRunUserRepository testRunUserRepository) {
        this.testRunUserRepository = testRunUserRepository;
    }

    @Override
    public List<TestRunUser> getTestRunUsersByTestRunId(Long testRunId) {
        return testRunUserRepository.findByTestRunIdOrderByAssignToUserId(testRunId);
    }

    @Override
    public StatusCounts getStatusCounts(List<TestRunUser> testRunUsers) {

        int untestedCount = 0;
        int passedCount = 0;
        int failedCount = 0;
        int blockedCount = 0;
        int retestCount = 0;

        for (TestRunUser testRunUser : testRunUsers) {
            String status = testRunUser.getStatus();
            if (status.equals("Passed")) {
                passedCount++;
            } else if (status.equals("Failed")) {
                failedCount++;
            } else if (status.equals("Untested")) {
                untestedCount++;
            } else if (status.equals("Blocked")) {
                blockedCount++;
            } else if (status.equals("Retest")) {
                retestCount++;
            }
        }

        int totalCount = testRunUsers.size();

        // Store status count in data class
        StatusCounts statusCounts = new StatusCounts();
        statusCounts.setBlockedCount(blockedCount);
        statusCounts.setFailedCount(failedCount);
        statusCounts.setPassedCount(passedCount);
        statusCounts.setRetestCount(retestCount);
        statusCounts.setUntestedCount(untestedCount);
        statusCounts.setTotalCount(totalCount);

        return statusCounts;
    }

    @Override
    public List<TestRunsOverviewDto> getDataToShowTestRunOverview(List<TestRun> testRuns) {

        int passedCount = 0;
        int totalCount = 0;
        List<TestRunsOverviewDto> testRunsOverviewDtos = new ArrayList<>();

        for (TestRun testRun : testRuns) {
            for (TestRunUser testRunUser : testRun.getTestRunUsers()) {
                String status = testRunUser.getStatus();
                if (status.equals("Passed"))
                    passedCount++;
            }
            TestRunsOverviewDto testRunsOverviewDto = new TestRunsOverviewDto();
            totalCount = testRun.getTestRunUsers().size();
            testRunsOverviewDto.setId(testRun.getId());
            testRunsOverviewDto.setName(testRun.getName());
            testRunsOverviewDto.setUser(testRun.getInitiator().getUsername());
            testRunsOverviewDto.setPassedCount(passedCount);
            testRunsOverviewDto.setTotalCount(totalCount);
            testRunsOverviewDtos.add(testRunsOverviewDto);
            passedCount = 0;
            totalCount = 0;
        }
        return testRunsOverviewDtos;
    }
}
