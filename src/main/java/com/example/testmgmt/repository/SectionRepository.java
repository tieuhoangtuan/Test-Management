package com.example.testmgmt.repository;

import com.example.testmgmt.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Section getSectionById(Long id);

    @Query("SELECT se FROM Section se WHERE se.parentSection is NULL and se.project.id = :projectId")
    List<Section> getAllByParentSectionByProjectId(@Param("projectId") Long projectId);

    List<Section> findAllByProjectId(Long projectId);

    Section findById(String id);

}
