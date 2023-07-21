package com.example.testmgmt.controller;
import com.example.testmgmt.entity.*;
import com.example.testmgmt.service.milestone.MilestoneService;
import com.example.testmgmt.service.project.ProjectService;
import com.example.testmgmt.service.project_user.ProjectUserService;
import com.example.testmgmt.service.role.RoleService;
import com.example.testmgmt.service.section.SectionService;
import com.example.testmgmt.service.test_run.TestRunService;
import com.example.testmgmt.service.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectUserService projectUserService;
    private final RoleService roleService;
    private final SectionService sectionService;
    private final MilestoneService milestoneService;
    private final TestRunService testRunService;

    public ProjectController(UserService userService, ProjectService projectService,
                             ProjectUserService projectUserService, RoleService roleService,
                             SectionService sectionService, MilestoneService milestoneService, TestRunService testRunService) {
        this.userService = userService;
        this.projectService = projectService;
        this.projectUserService = projectUserService;
        this.roleService = roleService;
        this.sectionService = sectionService;
        this.milestoneService = milestoneService;
        this.testRunService = testRunService;
    }
    @GetMapping
    public String showProjects(Model model) {
        User user = userService.getCurrentUser();
        Long userId = user.getId();
        model.addAttribute("projectList", projectService.findProjectByUserId(userId));
        return "project";
    }

    @GetMapping("/create")
    public String showCrateProject(Model model) {
        model.addAttribute("project", new Project());
        return "add_project";
    }

    @PostMapping("/create")
    public String processCreateProject(@NotNull Project project) {
        User user = userService.getCurrentUser();
        projectService.createProject(user, project);
        return "redirect:/project";
    }

    @GetMapping("/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectUserService.deleteByProjectId(id);
        return "redirect:/project";
    }

    @GetMapping("/edit/{projectId}")
    public String editProject(@PathVariable Long projectId, Model model){
        List<ProjectUser> projectUser = projectService.getProjectUser(projectId);
        List<Role> role = roleService.getAllRole();
        Project project = projectService.getProjectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("projectUser", projectUser);
        model.addAttribute("role", role);
        return "edit_project";
    }

    @PostMapping("/edit/{projectId}")
    public String editProject(@PathVariable Long projectId, @RequestParam("name") String name,
                              @RequestParam("emails") List<String> emails,
                              @RequestParam("roles") List<String> roles){
        projectService.editProject(projectId, name, emails, roles);
        return "redirect:/project";
    }

    @GetMapping("/overview/{projectId}")
    public String showOverView(@PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        List<Milestone> milestones = milestoneService.getActiveMilestones(projectId);
        List<TestRun> testRuns = testRunService.getAllTestRunByProjectId(projectId);
        model.addAttribute("milestones",milestones);
        model.addAttribute("testRuns", testRuns);
        model.addAttribute("project", project);
        return "overview";
    }
}

