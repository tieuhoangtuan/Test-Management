package com.example.testmgmt.repository;

import com.example.testmgmt.entity.TestRunUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRunUserRepository extends JpaRepository<TestRunUser, Long> {
    List<TestRunUser> findByTestRunIdOrderByAssignToUserId(Long testRunId);

    List<TestRunUser> findAllByTestRunId(Long id);
}
