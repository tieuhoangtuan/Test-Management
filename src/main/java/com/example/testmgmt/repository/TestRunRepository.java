package com.example.testmgmt.repository;

import com.example.testmgmt.entity.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRunRepository extends JpaRepository<TestRun, Long> {

    List<TestRun> findAllByProjectId(Long projectId);

    @Query("SELECT tr FROM TestRun tr WHERE tr.project.id = :projectId AND tr.id = :id")
    TestRun findByIdAndProjectId(Long projectId, Long id);

    @Query("select tr from TestRun tr WHERE tr.milestone.id = :milestoneId")
    List<TestRun> getAllTestRunByMilestoneId(Long milestoneId);
}
