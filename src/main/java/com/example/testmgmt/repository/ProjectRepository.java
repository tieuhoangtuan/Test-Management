package com.example.testmgmt.repository;

import com.example.testmgmt.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByProjectUserUserId(Long id);

    Project getProjectById(Long id);
}
