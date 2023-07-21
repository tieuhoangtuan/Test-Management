package com.example.testmgmt.controller;

import com.example.testmgmt.dto.TestCaseDataToCsv;
import com.example.testmgmt.dto.TestCaseDto;
import com.example.testmgmt.entity.Section;
import com.example.testmgmt.entity.TestCase;
import com.example.testmgmt.service.cases.CaseService;
import com.example.testmgmt.service.csv.CSVService;
import com.example.testmgmt.service.section.SectionService;
import com.example.testmgmt.utils.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.io.IOException;

import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("/cases")
public class TestCaseController {

    private final SectionService sectionService;
    private final CaseService caseService;
    private final CSVService csvService;

    public TestCaseController(SectionService sectionService, CaseService caseService,
                              CSVService csvService) {
        this.sectionService = sectionService;
        this.caseService = caseService;
        this.csvService = csvService;
    }

    @GetMapping("/create/{projectId}")
    public String showCreateCase(@PathVariable Long projectId, Model model) {
        List<Section> listSection = sectionService.getAllSectionsByProjectId(projectId);
        model.addAttribute("testCase", new TestCase());
        model.addAttribute("projectId", projectId);
        model.addAttribute("listSection", listSection);
        return "add_test_case";
    }

    @PostMapping("/create/{projectId}")
    public String processCreateTestCase(@PathVariable Long projectId,
                                        @NotNull TestCase testCase,
                                        @RequestParam("section_id") String sectionId) {
        System.out.println(sectionId);
        Section section = sectionService.getSectionById(sectionId);
        caseService.createCase(testCase, section);
        return "redirect:/suites/view/{projectId}";
    }

    @GetMapping("/update/{projectId}/{caseId}")
    public String showUpdateTestCase(@PathVariable Long projectId,
                                     @PathVariable Long caseId,
                                     Model model) {

        List<Section> listSection = sectionService.getAllSectionsByProjectId(projectId);
        Optional<TestCase> foundCase = caseService.getCaseByProjectIdAndCaseId(projectId, caseId);
        List<String> listType = new ArrayList<>();
        List<String> listPriority = new ArrayList<>();

        listType.add("Type1");
        listType.add("Type2");
        listType.add("Type3");

        listPriority.add("Critical");
        listPriority.add("High");
        listPriority.add("Medium");
        listPriority.add("Low");

        if (foundCase.isPresent()) {
            model.addAttribute("testCase", foundCase.get());
            model.addAttribute("listSection", listSection);
            model.addAttribute("listType", listType);
            model.addAttribute("listPriority", listPriority);
            return "update_test_case";
        } else {
            model.addAttribute("notFound", "404 Not Found");
            return "message";
        }
    }

    @PostMapping("/update/{projectId}/{caseId}")
    public String processUpdateTestCase(@PathVariable Long projectId,
                                        @PathVariable Long caseId,
                                        @ModelAttribute("testCase") TestCase testCase) {
        System.out.println(projectId);
        System.out.println(caseId);
        Optional<TestCase> editCase = caseService.getCaseByProjectIdAndCaseId(projectId, caseId);
        if (editCase.isPresent()) {
            TestCase oldObj = editCase.get();
            caseService.updateCase(testCase, oldObj);
        }
        return "redirect:/suites/view/{projectId}";
    }

    @GetMapping("/delete/{projectId}/{caseId}")
    public String deleteTestCase(@PathVariable Long caseId, @PathVariable String projectId) {
        caseService.deleteCase(caseId);
        return "redirect:/suites/view/{projectId}";
    }

    @PostMapping("/binding-csv/{projectId}/{sectionId}")
    public String bindingCsvFile(@PathVariable Long projectId,
                                 @PathVariable Long sectionId,
                                 @RequestParam("importFile") MultipartFile file,
                                 Model model)
            throws IOException, CsvException {

        String[] header = csvService.getHeader(file);

        List<String[]> rowsData = csvService.getRowData(file);
        ObjectMapper objectMapper = new ObjectMapper();
        String rowsDataJson = objectMapper.writeValueAsString(rowsData);

        List<String> fieldNames = Helper.getFieldName(TestCaseDto.class);

        model.addAttribute("headers", header);
        model.addAttribute("rowsDataJson", rowsDataJson);
        model.addAttribute("fieldNames", fieldNames);

        return "binding_csv";
    }

    @PostMapping("/post-binding-csv/{projectId}/{sectionId}")
    public String postProcessBindingCsv(@PathVariable Long projectId,
                                        @PathVariable Long sectionId,
                                        @RequestParam List<String> fields,
                                        @RequestParam("rowsData") String rowsDataJson,
                                        Model model)
            throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        TestCaseDto testCaseDto = new TestCaseDto();
        List<String> listType = new ArrayList<>();
        List<String> listPriority = new ArrayList<>();

        // Convert rowsData to Json
        ObjectMapper objectMapper = new ObjectMapper();
        List<String[]> rowsData = objectMapper.readValue(rowsDataJson, new TypeReference<List<String[]>>() {
        });

        // mapping order between csv file and entity fields
        Map<Integer, String> fieldOrderMap = csvService.getFieldOrderMap(fields);

        // set data from csv file to entity field following order after mapping
        List<TestCaseDto> lstCases = caseService.getDataFromCsvFile(fieldOrderMap, rowsData);
        testCaseDto.setTestCaseDtoList(lstCases);

        List<Section> lstSection = sectionService.getAllSectionsByProjectId(projectId);

        Section currentSection = sectionService.getSectionById(String.valueOf(sectionId));

        listType.add("Type1");
        listType.add("Type2");
        listType.add("Type3");

        listPriority.add("Critical");
        listPriority.add("High");
        listPriority.add("Medium");
        listPriority.add("Low");

        model.addAttribute("listSection", lstSection);
        model.addAttribute("currentSection", currentSection);
        model.addAttribute("listCases", testCaseDto);
        model.addAttribute("listType", listType);
        model.addAttribute("listPriority", listPriority);
        model.addAttribute("project_id", projectId);
        return "show_data_csv";
    }

    @PostMapping("/save-csv")
    public String saveDataFromCsv(@ModelAttribute TestCaseDto listCases,
                                  @RequestParam("section_id") List<Long> sectionId,
                                  @RequestParam("project_id") Long projectId) {

        List<TestCaseDto> lstCase = listCases.getTestCaseDtoList();
        List<Section> lstSection = sectionService.findAllById(sectionId);
        List<TestCase> caseList = caseService.assignDataFromCsv(lstCase, lstSection);
        caseService.saveAll(caseList);
        return "redirect:/suites/view/" + projectId;

    }

    @PostMapping("/export")
    public String exportToCsv(@RequestParam(name = "column")
                              List<String> selectedColumns,
                              @RequestParam(name = "selected_section", required = false)
                              Long sectionId,
                              @RequestParam(name = "project_id", required = false)
                              Long projectId,
                              HttpServletResponse response) {

        // Get data from service layer
        List<TestCase> listTestCase = caseService.getTestCaseBySectionId(projectId, sectionId);
        List<TestCaseDataToCsv> csvList = caseService.getForeignKeyData(listTestCase);

        // Map selected column names to entity field names
        Map<String, String> columnMap = new HashMap<>();
        columnMap.put("id", "id");
        columnMap.put("title", "title");
        columnMap.put("type", "type");
        columnMap.put("priority", "priority");
        columnMap.put("estimate", "estimate");
        columnMap.put("precondition", "precondition");
        columnMap.put("step", "step");
        columnMap.put("expectedResult", "expectedResult");
        columnMap.put("createdBy", "createdBy");
        columnMap.put("section", "section");

        // Create CSV writer and write header row
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data.csv\"");
        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            writer.writeNext(selectedColumns.toArray(new String[0]));

            // Write data rows
            for (TestCaseDataToCsv entity : csvList) {
                List<String> row = new ArrayList<>();
                for (String column : selectedColumns) {
                    String fieldName = columnMap.get(column);
                    Field field = TestCaseDataToCsv.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    row.add(value != null ? value.toString() : "");
                }
                writer.writeNext(row.toArray(new String[0]));
            }
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            // Handle exceptions
        }

        return "redirect:/";
    }

    @PostMapping("/{projectId}/copy")
    public String copyTestCase(@RequestParam("caseId")Long caseId,
                               @RequestParam("sectionId")Long sectionId,
                               @PathVariable String projectId){
        caseService.copyTestCase(caseId, sectionId);
        return "redirect:/suites/view/{projectId}";
    }

    @GetMapping("/get/{projectId}/{sectionId}")
    @ResponseBody
    public List<TestCaseDataToCsv> loadTestCaseFromSectionAjax(@PathVariable Long projectId,
                                              @PathVariable Long sectionId) {

        List<TestCase> testCases = caseService.getTestCaseBySectionId(projectId, sectionId);

        List<TestCaseDataToCsv> list = caseService.getForeignKeyData(testCases);

        return list;
    }
}
