package com.example.testmgmt.controller;

import com.example.testmgmt.dto.CommentDto;
import com.example.testmgmt.dto.TestResultDto;
import com.example.testmgmt.service.comment.CommentService;
import com.example.testmgmt.service.project_user.ProjectUserService;
import com.example.testmgmt.service.result.TestResultService;
import com.example.testmgmt.service.result_comment.ResultCommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests")
public class TestResultController {
    private final TestResultService testResultService;
    private final ProjectUserService projectUserService;
    private final ResultCommentService resultCommentService;
    private final CommentService commentService;

    public TestResultController(TestResultService testResultService, ProjectUserService projectUserService, ResultCommentService resultCommentService, CommentService commentService) {
        this.testResultService = testResultService;
        this.projectUserService = projectUserService;
        this.resultCommentService = resultCommentService;
        this.commentService = commentService;
    }

    @PostMapping("/add_comment/{projectId}/{testRunUserId}")
    public String addComment(CommentDto commentDto,
                             @PathVariable("testRunUserId")Long testRunUserId,
                             @PathVariable("projectId") Long projectId){
        commentService.AddComment(commentDto, testRunUserId);
        return "redirect:/tests/view/{projectId}/{testRunUserId}";
    }

    @PostMapping("/add/{projectId}/{testRunUserId}")
    public String addTestResult(TestResultDto testResultDto,
                                @RequestParam("testRunUserId")Long testRunUserId,
                                @PathVariable("projectId") Long projectId){
        testResultService.addTestResult(testResultDto);
        return "redirect:/tests/view/{projectId}/{testRunUserId}";
    }

    @GetMapping("/view/{projectId}/{testRunUserId}")
    public String testResult(Model model, @PathVariable("testRunUserId") Long testRunUserId, @PathVariable("projectId") Long projectId){
        model.addAttribute("resultComments", resultCommentService.getResultAndCommentSortedByDate(testRunUserId));
        model.addAttribute("testRunUser", testResultService.findTesRunUser(testRunUserId));
        model.addAttribute("projectUsers", projectUserService.findByProjectId(projectId));
        model.addAttribute("projectId", projectId);
        model.addAttribute("tesResultDto", new TestResultDto());
        model.addAttribute("commentDto", new CommentDto());
        return "testresult";
    }
}
