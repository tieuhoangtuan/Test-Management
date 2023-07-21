package com.example.testmgmt.service.project_user;

import com.example.testmgmt.entity.ProjectUser;
import com.example.testmgmt.entity.Role;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.entity.Project;
import com.example.testmgmt.repository.ProjectRepository;
import com.example.testmgmt.repository.ProjectUserRepository;
import com.example.testmgmt.repository.RoleRepository;
import com.example.testmgmt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectUserService {
    private final ProjectUserRepository projectUserRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;

    ProjectUserService(ProjectUserRepository projectUserRepository, UserRepository userRepository, ProjectRepository projectRepository, RoleRepository roleRepository){
        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
    }

    public boolean existsUserByEmail(String email){
        return userRepository.existsUserByEmail(email);
    }

    public void addUser(HttpServletRequest request, String fullName, String email) throws MessagingException, UnsupportedEncodingException {
        String password = generateRandomPassword();
        String code = RandomString.make(45);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        newUser.setActive(false);
        newUser.setEnable(false);
        User savedUser = userRepository.save(newUser);

        Role role = roleRepository.findRoleByRole("Lead");
        List<Project> listProject = projectRepository.findAll();
        for(Project p: listProject){
            ProjectUser newProjectUser = new ProjectUser();
            newProjectUser.setProject(p);
            newProjectUser.setUser(savedUser);
            newProjectUser.setRole(role);
            projectUserRepository.save(newProjectUser);
        }
    }

    public String addMultipleUser(HttpServletRequest request, String rawListUser) throws MessagingException, UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<String, String>();
        List<String> listUser = List.of(rawListUser.split("\r\n"));
        for(String user: listUser){
            try{
                String[] parts = user.split(",");
                map.put(parts[0].trim(), parts[1].trim());
            } catch (ArrayIndexOutOfBoundsException ex){
                System.out.println("Invalid line format (use: full name, email)");
                return "Invalid line format (use: full name, email)";
            }
        }

        for (Map.Entry<String, String> item : map.entrySet()) {
            if (existsUserByEmail(item.getValue())){
                String errorMessage = "The Email Address: " + item.getValue() + " is already in use by another user.";
                return errorMessage;
            } else {
                addUser(request, item.getKey(), item.getValue());
            }
        }
        String successfulMessage = "Successfully added the new user and sent an invitation email.";
        return successfulMessage;
    }

    public String generateRandomPassword() {
        int length = 10;
        String passwordChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * passwordChars.length());
            password.append(passwordChars.charAt(index));
        }
        return password.toString();
    }

    @Transactional
    public void deleteByProjectId(Long projectId) {
        List<ProjectUser> projectUser = projectUserRepository.findByProjectId(projectId);
        if (projectUser != null) {
            for (ProjectUser projectUser1 : projectUser) {
                projectUserRepository.delete(projectUser1);
            }
        }
    }

    public  List<ProjectUser> findByProjectId(Long id) {
        return projectUserRepository.findByProjectId(id);
    }

    public List<ProjectUser> getAllRecordByProjectId(Long projectId) {
        return projectUserRepository.findByProjectId(projectId);
    }
}
