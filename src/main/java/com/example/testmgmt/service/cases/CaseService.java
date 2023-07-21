package com.example.testmgmt.service.cases;

import com.example.testmgmt.dto.TestCaseDataToCsv;
import com.example.testmgmt.dto.TestCaseDto;
import com.example.testmgmt.entity.Section;
import com.example.testmgmt.entity.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CaseService {

    void createCase(TestCase testCase, Section section);

    void copyTestCase(Long testCaseId, Long sectionId);

    void updateCase(TestCase newObj, TestCase oldObj);

    Optional<TestCase> getCaseByProjectIdAndCaseId(Long projectId, Long caseId);

    void deleteCase(Long caseId);

    List<TestCaseDto> getDataFromCsvFile(Map<Integer, String> fieldOrderMap,
                                         List<String[]> rowsData)
            throws NoSuchFieldException, IllegalAccessException;

    List<TestCase> assignDataFromCsv(List<TestCaseDto> testCaseDtoList, List<Section> listSection);

    void saveAll(List<TestCase> listTestCase);

    List<TestCase> getTestCaseBySectionId(Long projectId, Long sectionId);

    List<TestCaseDataToCsv> getForeignKeyData(List<TestCase> listTestCases);

}
