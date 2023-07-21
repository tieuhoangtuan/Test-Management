package com.example.testmgmt.service.test_run;
import com.example.testmgmt.entity.*;
import com.example.testmgmt.repository.*;
import com.example.testmgmt.service.user.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestRunServiceImpl implements TestRunService {

    private TestRunRepository testRunRepository;
    private MilestoneRepository milestoneRepository;
    private UserRepository userRepository;
    private CaseRepository caseRepository;
    private UserService userService;
    private TestRunUserRepository testRunUserRepository;
    private ProjectRepository projectRepository;

    public TestRunServiceImpl(TestRunRepository testRunRepository,
                              MilestoneRepository milestoneRepository,
                              UserRepository userRepository,
                              CaseRepository caseRepository,
                              UserService userService,
                              TestRunUserRepository testRunUserRepository,
                              ProjectRepository projectRepository) {
        this.testRunRepository = testRunRepository;
        this.milestoneRepository = milestoneRepository;
        this.userRepository = userRepository;
        this.caseRepository = caseRepository;
        this.userService = userService;
        this.testRunUserRepository = testRunUserRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void createTestRun(TestRun testRun, Long milestoneId,
                              Long assignUserId, int request,
                              List<String> testCaseList, Long projectId) {

        // convert list id of testcase from string to long
        List<Long> convertedId = testCaseList.stream()
                .map(Long :: valueOf).toList();

        User initiator = userService.getCurrentUser();
        List<TestCase> selectedTestCase = caseRepository.findAllById(convertedId);
        List<TestCase> getAllCasesByProjectId = caseRepository.findAllByProjectId(projectId);

        // assign for test run once time before loop
        Milestone milestone = null;
        if (milestoneId != null)
            milestone = milestoneRepository.findById(milestoneId).orElse(null);
        testRun.setInitiator(initiator);

        User assignUser = null;
        if (assignUserId != null)
            assignUser = userRepository.findById(assignUserId).orElse(null);
        testRun.setMilestone(milestone);

        Project project = projectRepository.findById(projectId).orElse(null);

        testRun.setProject(project);


        switch (request) {
            // update case
            case 0 -> {
                List<TestRunUser> testRunUserList = testRunUserRepository.findAllByTestRunId(testRun.getId());
                for (TestRunUser testRunUser : testRunUserList) {
                    testRunUser.setAssignToUser(assignUser);
                    testRunUser.setTestRun(testRun);
                    testRunUserRepository.save(testRunUser);
                }
            }
            // include all test case
            case 1 -> {
                    if (testRun.getId() == null) {
                        for (TestCase testCase : getAllCasesByProjectId) {
                            TestRunUser testRunUser = new TestRunUser();
                            testRunUser.setStatus("Untested");
                            testRunUser.setAssignToUser(assignUser);
                            testRunUser.setTestCase(testCase);
                            testRunUser.setTestRun(testRun);
                            testRunUserRepository.save(testRunUser);
                        }
                    } else {
                        List<TestRunUser> testRunUserList = testRunUserRepository.findAllByTestRunId(testRun.getId());
                        for (TestCase testCase : getAllCasesByProjectId) {
                            for (TestRunUser testRunUser : testRunUserList) {
                                testRunUser.setAssignToUser(assignUser);
                                testRunUser.setTestRun(testRun);
                                testRunUser.setTestCase(testCase);
                                testRunUser.setStatus("Untested");
                                testRunUserRepository.save(testRunUser);
                            }
                        }
                    }
            }
            // include specify test case
            case 2 -> {
                if (testRun.getId() == null) {
                    for (TestCase testCase : selectedTestCase) {
                        TestRunUser testRunUser = new TestRunUser();
                        testRunUser.setStatus("Untested");
                        testRunUser.setAssignToUser(assignUser);
                        testRunUser.setTestCase(testCase);
                        testRunUser.setTestRun(testRun);
                        testRunUserRepository.save(testRunUser);
                    }
                } else {
                        List<TestRunUser> testRunUserList = testRunUserRepository.findAllByTestRunId(testRun.getId());
                        for (TestCase testCase : selectedTestCase) {
                            for (TestRunUser testRunUser : testRunUserList) {
                                testRunUser.setAssignToUser(assignUser);
                                testRunUser.setTestRun(testRun);
                                testRunUser.setTestCase(testCase);
                                testRunUser.setStatus("Untested")   ;
                                testRunUserRepository.save(testRunUser);
                            }
                        }
                    }
                }
            }
        }

    @Override
    public List<TestRun> getAllTestRunByProjectId(Long projectId) {
        return testRunRepository.findAllByProjectId(projectId);
    }

    @Override
    public TestRun getTestRunByIdAndProjectId(Long projectId, Long id) {
        return testRunRepository.findByIdAndProjectId(projectId, id);
    }

    @Override
    public void deleteTestRun(Long runId) {

//        List<TestRunUser> testRunUserList = testRunUserRepository.findAllByTestRunId(runId);
//
//        if (testRunUserList != null)
//            testRunUserRepository.deleteAll(testRunUserList);
//
        testRunRepository.deleteById(runId);
    }

    @Override
    public List<TestRun> getAllTestRunByMilestoneId(Long milestoneId) {
        return testRunRepository.getAllTestRunByMilestoneId(milestoneId);
    }
}
