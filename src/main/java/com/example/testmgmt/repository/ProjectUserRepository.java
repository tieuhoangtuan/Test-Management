package com.example.testmgmt.repository;

import com.example.testmgmt.entity.Project;
import com.example.testmgmt.entity.ProjectUser;
import com.example.testmgmt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    @Query("SELECT pu FROM ProjectUser pu WHERE pu.project.id = :projectId")
    List<ProjectUser> findByProjectId(@Param("projectId") Long projectId);

    List<ProjectUser> findAllByProject(Project project);

    @Query("SELECT pu FROM ProjectUser pu Where pu.project = :project AND pu.user = :user ")
    ProjectUser findByProjectAndUser(@Param("project") Project project, @Param("user") User user);
}
