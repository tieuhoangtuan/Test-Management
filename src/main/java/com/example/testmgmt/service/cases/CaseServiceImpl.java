package com.example.testmgmt.service.cases;

import com.example.testmgmt.dto.TestCaseDataToCsv;
import com.example.testmgmt.dto.TestCaseDto;
import com.example.testmgmt.entity.Section;
import com.example.testmgmt.entity.TestCase;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.CaseRepository;
import com.example.testmgmt.repository.SectionRepository;
import com.example.testmgmt.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class CaseServiceImpl implements CaseService{

    private final CaseRepository caseRepository;
    private final UserService userService;

    private final SectionRepository sectionRepository;


    @Autowired
    public CaseServiceImpl(CaseRepository caseRepository, UserService userService, SectionRepository sectionRepository) {
        this.caseRepository = caseRepository;
        this.userService = userService;
        this.sectionRepository = sectionRepository;
    }

    @Override
    public void saveAll(List<TestCase> listTestCase) {
        caseRepository.saveAll(listTestCase);
    }

    @Override
    public void createCase(TestCase testCase, Section section) {
        User currentUser = userService.getCurrentUser();
        testCase.setCreatedAt(LocalDateTime.now());
        testCase.setCreatedBy(currentUser);
        testCase.setSection(section);
        caseRepository.save(testCase);
    }

    @Override
    public void copyTestCase(Long testCaseId, Long sectionId) {
        TestCase existedTestCase = caseRepository.findById(testCaseId).orElseThrow();
        Section existedSection = sectionRepository.getSectionById(sectionId);
        User currentUser = userService.getCurrentUser();
        TestCase testCaseCopy = new TestCase(existedTestCase);
        testCaseCopy.setSection(existedSection);
        testCaseCopy.setCreatedBy(currentUser);
        testCaseCopy.setCreatedAt(LocalDateTime.now());
        caseRepository.save(testCaseCopy);
    }

    @Override
    public Optional<TestCase> getCaseByProjectIdAndCaseId(Long projectId, Long caseId) {
        return caseRepository.findByProjectIdAndTestCaseId(projectId, caseId);
    }

    @Override
    public void deleteCase(Long caseId) {

        caseRepository.deleteById(caseId);

    }

    @Override
    public void updateCase(TestCase newObj, TestCase oldObj) {

        User currentUser = userService.getCurrentUser();

        oldObj.setUpdatedAt(LocalDateTime.now());
        oldObj.setTitle(newObj.getTitle());
        oldObj.setEstimate(newObj.getEstimate());
        oldObj.setExpectedResult(newObj.getExpectedResult());
        oldObj.setPrecondition(newObj.getPrecondition());
        oldObj.setPriority(newObj.getPriority());
        oldObj.setStep(newObj.getStep());
        oldObj.setType(newObj.getType());
        oldObj.setUpdatedBy(currentUser);

        caseRepository.save(oldObj);
    }

    @Override
    public List<TestCaseDto> getDataFromCsvFile(Map<Integer, String> fieldOrderMap,
                                                List<String[]> rowsData)
            throws NoSuchFieldException, IllegalAccessException {

        List<TestCaseDto> lstTestCases = new ArrayList<>();

        for (String[] rows : rowsData) {

            TestCaseDto testCaseEntity = new TestCaseDto();
            for (Map.Entry<Integer, String> entry : fieldOrderMap.entrySet()) {

                String fieldName = entry.getValue();
                Field field = testCaseEntity.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);

                switch (entry.getKey()) {
                    case 1 -> field.set(testCaseEntity, rows[0]);
                    case 2 -> field.set(testCaseEntity, rows[1]);
                    case 3 -> field.set(testCaseEntity, rows[2]);
                    case 4 -> field.set(testCaseEntity, rows[3]);
                    case 5 -> field.set(testCaseEntity, rows[4]);
                    case 6 -> field.set(testCaseEntity, rows[5]);
                    case 7 -> field.set(testCaseEntity, rows[6]);
                }
            }

            lstTestCases.add(testCaseEntity);

        }

        return lstTestCases;
    }

    @Override
    public List<TestCase> assignDataFromCsv(List<TestCaseDto> testCaseDtoList,
                                                   List<Section> listSection) {

        List<TestCase> listTestCases = new ArrayList<>();

        User currentUser = userService.getCurrentUser();

        for (int i = 0; i < testCaseDtoList.size(); i++) {

            TestCaseDto testCaseDto = testCaseDtoList.get(i);
            Section section = listSection.get(i);

            TestCase testCase = new TestCase();
            testCase.setTitle(testCaseDto.getTitle());
            testCase.setType(testCaseDto.getType());
            testCase.setPriority(testCaseDto.getPriority());
            testCase.setEstimate(testCaseDto.getEstimate());
            testCase.setPrecondition(testCaseDto.getPrecondition());
            testCase.setStep(testCaseDto.getStep());
            testCase.setExpectedResult(testCaseDto.getExpectedResult());
            testCase.setCreatedBy(currentUser);
            testCase.setCreatedAt(LocalDateTime.now());
            testCase.setSection(section);

            listTestCases.add(testCase);

        }

        return listTestCases;
    }

    @Override
    public List<TestCase> getTestCaseBySectionId(Long projectId, Long sectionId) {

        if (sectionId != null)
            return caseRepository.findBySectionId(sectionId);
        return caseRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<TestCaseDataToCsv> getForeignKeyData(List<TestCase> listTestCases) {

        List<TestCaseDataToCsv> csvList = new ArrayList<>();

        for (TestCase caseList : listTestCases) {

            TestCaseDataToCsv data = new TestCaseDataToCsv();

            data.setId(caseList.getId());
            data.setTitle(caseList.getTitle());
            data.setType(caseList.getType());
            data.setPriority(caseList.getPriority());
            data.setEstimate(caseList.getEstimate());
            data.setPrecondition(caseList.getPrecondition());
            data.setStep(caseList.getStep());
            data.setExpectedResult(caseList.getExpectedResult());
            data.setCreatedBy(caseList.getCreatedBy().getEmail());
            data.setSection(caseList.getSection().getName());

            csvList.add(data);
        }

        return csvList;
    }
}
