package com.example.testmgmt.controller;

import com.example.testmgmt.dto.SectionDto;
import com.example.testmgmt.entity.Section;
import com.example.testmgmt.entity.TestCase;
import com.example.testmgmt.service.section.SectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/suites")
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService){
        this.sectionService = sectionService;
    }

    @GetMapping("/view/{projectId}")
    public String getSection(Model model, @PathVariable("projectId")Long projectId){
        List<Section> sections = sectionService.getSectionsByProject(projectId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("sections",sections);
        model.addAttribute("SectionDto", new SectionDto());
        model.addAttribute("testCase", new TestCase());
        return "sections";
    }

    @GetMapping("/view/{projectId}/delete/{id}")
    public String deleteSection(@PathVariable Long id, @PathVariable("projectId")Long projectId){
        sectionService.deleteSection(id);
        return "redirect:/suites/view/{projectId}";
    }
    @PostMapping("/view/{projectId}")
    public String addSection(SectionDto sectionDto,@PathVariable("projectId")Long projectId){
        sectionService.addSection(sectionDto, projectId);
        return "redirect:/suites/view/{projectId}";
    }

    @PostMapping("/view/edit/{projectId}")
    public String editSection(SectionDto sectionDto, @PathVariable String projectId){
        sectionService.editSection(sectionDto);
        return "redirect:/suites/view/{projectId}";
    }
}
