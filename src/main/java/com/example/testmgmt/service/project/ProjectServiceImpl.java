package com.example.testmgmt.service.project;


import com.example.testmgmt.entity.Project;
import com.example.testmgmt.entity.ProjectUser;
import com.example.testmgmt.entity.Role;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.ProjectRepository;
import com.example.testmgmt.repository.ProjectUserRepository;
import com.example.testmgmt.repository.RoleRepository;
import com.example.testmgmt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;

import java.util.List;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    @Autowired
    public ProjectServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ProjectRepository projectRepository, ProjectUserRepository projectUserRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
    }


    @Override
    public void createProject(User user, Project project) {
        ProjectUser projectUser = new ProjectUser();
        Role roleUser = roleRepository.findRoleByRole("Lead");
        projectUser.setRole(roleUser);
        projectUser.setProject(project);
        projectUser.setUser(user);
        projectUserRepository.save(projectUser);
    }

    @Override
    public List<Project> findProjectByUserId(Long userId) {
        return projectRepository.findByProjectUserUserId(userId);
    }

    @Override
    public void deleteByProjectId(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void editProject(Long projectId, String name, List<String> emails, List<String> roles) {
        Project existProject = projectRepository.getReferenceById(projectId);
        existProject.setName(name);
        projectRepository.save(existProject);
        for(int i = 0; i < emails.size(); i++) {
            String email = emails.get(i).replace("\r\n","");
            User existUser = userRepository.findByEmail(email);
            String role = roles.get(i);
            Role existRole = roleRepository.findRoleByRole(role);
            ProjectUser projectUser = projectUserRepository.findByProjectAndUser(existProject, existUser);
            projectUser.setRole(existRole);
            projectUserRepository.save(projectUser);
        }
    }

    @Override
    public List<ProjectUser> getProjectUser(Long projectId) {
        Project project = projectRepository.getProjectById(projectId);
        return projectUserRepository.findAllByProject(project);
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.getProjectById(projectId);
    }
}
