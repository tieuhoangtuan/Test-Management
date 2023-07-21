package com.example.testmgmt.controller;
import com.example.testmgmt.data_container.StatusCounts;
import com.example.testmgmt.dto.TestRunsOverviewDto;
import com.example.testmgmt.entity.*;
import com.example.testmgmt.service.milestone.MilestoneService;
import com.example.testmgmt.service.project_user.ProjectUserService;
import com.example.testmgmt.service.section.SectionService;
import com.example.testmgmt.service.test_run.TestRunService;
import com.example.testmgmt.service.test_run_user.TestRunUserService;
import com.example.testmgmt.utils.Helper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/runs")
public class TestRunController {

    private SectionService sectionService;
    private ProjectUserService projectUserService;
    private MilestoneService milestoneService;
    private TestRunService testRunService;
    private TestRunUserService testRunUserService;


    @Autowired
    public TestRunController(SectionService sectionService,
                             ProjectUserService projectUserService,
                             MilestoneService milestoneService,
                             TestRunService testRunService,
                             TestRunUserService testRunUserService) {
        this.sectionService = sectionService;
        this.projectUserService = projectUserService;
        this.milestoneService = milestoneService;
        this.testRunService = testRunService;
        this.testRunUserService = testRunUserService;
    }

    @GetMapping("/add/{projectId}")
    public String showAddTestRunPage(@PathVariable("projectId") Long id,
                                     Model model) {

        List<Section> sections = sectionService.getSectionsByProject(id);
        List<ProjectUser> listUserInProject = projectUserService.getAllRecordByProjectId(id);
        List<Milestone> listMilestone = milestoneService.getAllMilestoneByProjectId(id);
        String localDate = Helper.getLocalDay();
        TestRun testRun = new TestRun();
        testRun.setName("Test Run " + localDate);

        model.addAttribute("sections", sections);
        model.addAttribute("testRun", testRun);
        model.addAttribute("listUser", listUserInProject);
        model.addAttribute("listMilestone", listMilestone);
        model.addAttribute("project_id", id);

        return "add_test_run";
    }

    @PostMapping("add/{projectId}")
    public String processAddTestRun(@PathVariable("projectId") Long id,
                                    @ModelAttribute("testRun") @NotNull TestRun testRun,
                                    @RequestParam(name = "milestone_id", required = false) Long milestoneId,
                                    @RequestParam(name = "assign_id", required = false) Long assignUserId,
                                    @RequestParam("request") int request,
                                    @RequestParam(name = "testCaseList", required = false) List<String> listCase,
                                    Model model) {

        if (request == 0) {
            model.addAttribute("errorRequest", true);
            return "add_test_run";
        } else {
            testRunService.createTestRun(testRun, milestoneId, assignUserId,
                    request, listCase, id);
            return "redirect:/runs/overview/{projectId}";
        }
    }

    @GetMapping("overview/{projectId}")
    public String showOverview(@PathVariable Long projectId,
                               Model model) {


        List<TestRun> testRuns = testRunService.getAllTestRunByProjectId(projectId);
        List<TestRunsOverviewDto> testRunsOverviewDtos = testRunUserService.getDataToShowTestRunOverview(testRuns);

        model.addAttribute("testRuns", testRunsOverviewDtos);
        model.addAttribute("projectId", projectId);

        return "test_runs_and_results_page";
    }

    @GetMapping("edit/{projectId}/{runId}")
    public String showEdit(@PathVariable Long projectId,
                           @PathVariable Long runId,
                           Model model) {

        List<Section> sections = sectionService.getSectionsByProject(projectId);
        List<ProjectUser> listUserInProject = projectUserService.getAllRecordByProjectId(projectId);
        List<Milestone> listMilestone = milestoneService.getAllMilestoneByProjectId(projectId);

        model.addAttribute("sections", sections);
        model.addAttribute("listUser", listUserInProject);
        model.addAttribute("listMilestone", listMilestone);
        model.addAttribute("project_id", projectId);

        TestRun testRun = testRunService.getTestRunByIdAndProjectId(projectId, runId);

        model.addAttribute("testRun", testRun);

        return "update_test_run";
    }

    @PostMapping("edit/{projectId}/{runId}")
    public String processEdit(@PathVariable Long projectId,
                              @PathVariable Long runId,
                              @ModelAttribute("testRun") @NotNull TestRun testRun,
                              @RequestParam(name = "milestone_id", required = false) Long milestoneId,
                              @RequestParam(name = "assign_id", required = false) Long assignUserId,
                              @RequestParam("request") int request,
                              @RequestParam(name = "testCaseList", required = false) List<String> listCase) {

        testRunService.createTestRun(testRun, milestoneId, assignUserId,
                request, listCase, projectId);

        return "redirect:/runs/overview/{projectId}";
    }

    @GetMapping("delete/{projectId}/{runId}")
    public String processDelete(@PathVariable Long projectId,
                                @PathVariable Long runId) {

        testRunService.deleteTestRun(runId);

        return "redirect:/runs/overview/{projectId}";
    }

    @GetMapping("/detail/{projectId}/{runId}")
    public String showTestRunDetail(@PathVariable Long projectId,
                                    @PathVariable Long runId,
                                    Model model) {

        User unassignedUser = new User();
        unassignedUser.setUsername("Unassigned");

        TestRun run = testRunService.getTestRunByIdAndProjectId(projectId,runId);
        List<TestRunUser> testRunUsers = testRunUserService.getTestRunUsersByTestRunId(runId);

        StatusCounts statusCounts = testRunUserService.getStatusCounts(testRunUsers);

        Map<User, List<TestRunUser>> groupedUsers = testRunUsers.stream()
                .collect(Collectors.groupingBy
                        (testRunUser -> testRunUser.getAssignToUser() != null
                                ? testRunUser.getAssignToUser() : unassignedUser
                        ));

        model.addAttribute("run", run);
        model.addAttribute("testRunUsersByAssignUser", groupedUsers);
        model.addAttribute("statusCounts", statusCounts);

        return "test_run_detail";

    }
}
