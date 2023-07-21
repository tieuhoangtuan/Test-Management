package com.example.testmgmt.repository;

import com.example.testmgmt.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    @Query("SELECT tr FROM TestResult tr WHERE tr.testRunUser.id = :testRunUserId order by tr.createdDate desc ")
    List<TestResult> getAllByTestRunUserId(@Param("testRunUserId") Long testRunUserId);
}
