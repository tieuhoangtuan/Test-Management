package com.example.testmgmt.service.project;

import com.example.testmgmt.entity.Project;
import com.example.testmgmt.entity.ProjectUser;
import com.example.testmgmt.entity.User;
import java.util.List;



import java.util.List;

import java.util.List;

import java.util.List;

public interface ProjectService {
    void createProject(User user, Project project);

    List<Project> findProjectByUserId(Long userId);

    void deleteByProjectId(Long id);

    void editProject(Long projectId,String name,List<String> emails,List<String> roles);

    List<ProjectUser> getProjectUser(Long projectId);

    Project getProjectById(Long projectId);

}
