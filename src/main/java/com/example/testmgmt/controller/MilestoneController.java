package com.example.testmgmt.controller;

import com.example.testmgmt.data_container.StatusCounts;
import com.example.testmgmt.dto.MilestoneDto;
import com.example.testmgmt.dto.TestRunsOverviewDto;
import com.example.testmgmt.entity.Milestone;
import com.example.testmgmt.entity.TestRun;
import com.example.testmgmt.service.milestone.MilestoneService;
import com.example.testmgmt.service.test_run.TestRunService;
import com.example.testmgmt.service.test_run_user.TestRunUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private final MilestoneService milestoneService;
    private final TestRunService testRunService;
    private final TestRunUserService testRunUserService;

    public MilestoneController(MilestoneService milestoneService, TestRunService testRunService, TestRunUserService testRunUserService) {
        this.milestoneService = milestoneService;
        this.testRunService = testRunService;
        this.testRunUserService = testRunUserService;
    }

    @GetMapping("/overview/{projectId}")
    public String showMilestones(Model model, @PathVariable("projectId") Long projectId){
        List<Milestone> activeMilestones = milestoneService.getActiveMilestones(projectId);
        List<Milestone> upcomingMilestones = milestoneService.getUpcomingMilestones(projectId);
        List<Milestone> completedMilestones = milestoneService.getCompletedMilestones(projectId);
        model.addAttribute("activeMilestones", activeMilestones);
        model.addAttribute("upcomingMilestones", upcomingMilestones);
        model.addAttribute("completedMilestones", completedMilestones);
        model.addAttribute("projectId", projectId);
        return "milestone";
    }

    @GetMapping("/add/{projectId}")
    public String showAddMilestonePage(Model model, @PathVariable Long projectId, @RequestParam(value = "parent_id", required = false) Long parentMilestoneId){
        List<Milestone> activeMilestones = milestoneService.getActiveMilestones(projectId);
        List<Milestone> upcomingMilestones = milestoneService.getUpcomingMilestones(projectId);
        model.addAttribute("milestoneDto", new MilestoneDto());
        model.addAttribute("projectId", projectId);
        model.addAttribute("upcomingMilestones", upcomingMilestones);
        model.addAttribute("activeMilestones", activeMilestones);
        if (parentMilestoneId != null) {
            model.addAttribute("parent_id", parentMilestoneId);
        }

        return "add_milestone";
    }

    @PostMapping("/add/{projectId}")
    public String addMilestone(MilestoneDto milestoneDto, Model model, @PathVariable Long projectId) throws ParseException {
        milestoneService.addMilestone(milestoneDto);
        model.addAttribute("projectId", projectId);
        return "redirect:/milestones/overview/{projectId}";
    }

    @GetMapping("/view/{projectId}/delete/{milestoneId}")
    public String deleteMilestone(@PathVariable("milestoneId") Long milestoneId, @PathVariable("projectId")Long projectId){
        milestoneService.deleteMilestone(milestoneId);
        return "redirect:/milestones/overview/{projectId}";
    }

    @GetMapping("/{projectId}/edit/{milestoneId}")
    public String showEditMilestone(@PathVariable("milestoneId")Long milestoneId, @PathVariable("projectId")Long projectId, Model model){
        List<Milestone> activeMilestones = milestoneService.getActiveMilestones(projectId);
        List<Milestone> upcomingMilestones = milestoneService.getUpcomingMilestones(projectId);
        Milestone existedMilestone = milestoneService.getMilestoneById(milestoneId);
        model.addAttribute("milestoneDto", new MilestoneDto());
        model.addAttribute("milestoneId", milestoneId);
        model.addAttribute("milestone", existedMilestone);
        model.addAttribute("projectId", projectId);
        model.addAttribute("upcomingMilestones", upcomingMilestones);
        model.addAttribute("activeMilestones", activeMilestones);
        return "edit_milestone";
    }

    @PostMapping("/{projectId}/edit/{milestoneId}")
    public String editMilestone(MilestoneDto milestoneDto, @PathVariable("milestoneId")Long milestoneId, @PathVariable("projectId")Long projectId) throws ParseException {
        milestoneService.editMilestone(milestoneId, milestoneDto);
        return "redirect:/milestones/overview/{projectId}";
    }

    @GetMapping("/view/{projectId}/{milestoneId}")
    public String showMilestoneDetail(@PathVariable("milestoneId")Long milestoneId, @PathVariable("projectId")Long projectId,Model model){
        Milestone milestone = milestoneService.getMilestoneById(milestoneId);
        StatusCounts statusCounts = milestoneService.getStatusCountsByMilestone(milestoneId);
        List<TestRun> testRuns = testRunService.getAllTestRunByMilestoneId(milestoneId);
        List<TestRunsOverviewDto> testRunsOverviewDtoList = testRunUserService.getDataToShowTestRunOverview(testRuns);
        List<Milestone> subMilestonesList = milestoneService.getSubMilestoneListByParentId(projectId, milestoneId);
        model.addAttribute("milestone", milestone);
        model.addAttribute("testRuns", testRunsOverviewDtoList);
        model.addAttribute("statusCounts", statusCounts);
        model.addAttribute("projectId", projectId);
        model.addAttribute("subMilestones", subMilestonesList);
        return "milestone_detail";
    }

    @PostMapping("/start/{projectId}")
    public String startMilestone(@PathVariable("projectId") Long projectId,
                                 @RequestParam("startDate")String startDate,
                                 @RequestParam("endDate")String endDate,
                                 @RequestParam("id")Long id) throws ParseException {
        milestoneService.startMilestone(id, startDate, endDate);
        return "redirect:/milestones/overview/{projectId}";
    }
    @GetMapping("/get/{milestoneId}")
    @ResponseBody
    public MilestoneDto getMilestone(@PathVariable("milestoneId")Long milestoneId){
        return milestoneService.getMileDtoByMilestoneId(milestoneId);
    }
}
