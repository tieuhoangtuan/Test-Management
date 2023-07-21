package com.example.testmgmt.service.result;

import com.example.testmgmt.dto.TestResultDto;
import com.example.testmgmt.entity.TestResult;
import com.example.testmgmt.entity.TestRunUser;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.TestResultRepository;
import com.example.testmgmt.repository.TestRunUserRepository;
import com.example.testmgmt.repository.UserRepository;
import com.example.testmgmt.service.user.UserService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TestResultServiceImpl implements TestResultService{
    private final TestResultRepository testResultRepository;
    private final TestRunUserRepository testRunUserRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public TestResultServiceImpl(TestResultRepository testResultRepository, TestRunUserRepository testRunUserRepository, UserService userService, UserRepository userRepository) {
        this.testResultRepository = testResultRepository;
        this.testRunUserRepository = testRunUserRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void addTestResult(TestResultDto testResultDto) {
        TestResult newTestResult = new TestResult();
        TestRunUser existedTesRunUser = testRunUserRepository.findById(testResultDto.getTestRunUserId()).orElseThrow();
        User  assignUser;
        if(testResultDto.getAssignUser() != 0){
            assignUser = userRepository.findById(testResultDto.getAssignUser()).orElseThrow();
            newTestResult.setAssignUser(assignUser);
            existedTesRunUser.setAssignToUser(assignUser);

        }else{
            newTestResult.setAssignUser(null);
        }
        newTestResult.setStatus(testResultDto.getStatus());
        newTestResult.setUser(userService.getCurrentUser());
        newTestResult.setCreatedDate(LocalDateTime.now());
        newTestResult.setElapsedTime(testResultDto.getElapsedTime());
        newTestResult.setTestRunUser(existedTesRunUser);
        newTestResult.setComment(testResultDto.getComment());
        existedTesRunUser.setStatus(testResultDto.getStatus());
        testRunUserRepository.save(existedTesRunUser);
        testResultRepository.save(newTestResult);
    }

    @Override
    public TestRunUser findTesRunUser(Long id) {
        return testRunUserRepository.findById(id).orElseThrow();
    }

    @Override
    public List<TestResult> getAllTestResultByTestRunUserId(Long testRunUserId) {
        return testResultRepository.getAllByTestRunUserId(testRunUserId);
    }
}
