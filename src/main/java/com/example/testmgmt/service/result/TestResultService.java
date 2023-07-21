package com.example.testmgmt.service.result;

import com.example.testmgmt.dto.TestResultDto;
import com.example.testmgmt.entity.TestResult;
import com.example.testmgmt.entity.TestRunUser;

import java.util.List;

public interface TestResultService {
    void addTestResult(TestResultDto testResultDto);
    TestRunUser findTesRunUser(Long id);
    List<TestResult> getAllTestResultByTestRunUserId(Long testRunUserId);
}
