package com.example.testmgmt.repository;

import com.example.testmgmt.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CaseRepository extends JpaRepository<TestCase, Long> {

    @Query("SELECT tc FROM TestCase tc "
            + "JOIN tc.section s "
            + "JOIN s.project p "
            + "WHERE p.id = :projectId AND tc.id = :testCaseId")
    Optional<TestCase> findByProjectIdAndTestCaseId(@Param("projectId") Long projectId,
                                                    @Param("testCaseId") Long testCaseId);

    List<TestCase> findBySectionId(Long id);

    @Query("SELECT t FROM TestCase t JOIN t.section s JOIN s.project p WHERE p.id = :projectId ")
    List<TestCase> findAllByProjectId(@Param("projectId") Long projectId);

    List<TestCase> findAll();

}
