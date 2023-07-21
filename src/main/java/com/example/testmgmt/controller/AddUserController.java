package com.example.testmgmt.controller;

import com.example.testmgmt.service.project_user.ProjectUserService;
import com.example.testmgmt.service.role.RoleService;
import com.example.testmgmt.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/users")
public class AddUserController {
    private final ProjectUserService projectUserService;
    private final UserService userService;
    private final RoleService roleService;
    AddUserController(ProjectUserService projectUserService,UserService userService, RoleService roleService){
        this.projectUserService = projectUserService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/overview")
    public String showUserRole(Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRole());
        return "user_role";
    }

    @GetMapping("/add")
    public String showInviteForm(){
        return "add_user";
    }

    @GetMapping("/add_many")
    public String showInviteMultipleForm(){
        return "add_multi_user";
    }
    @PostMapping("/add")
    public String inviteUser(HttpServletRequest request, String fullName, String email, Model model) throws MessagingException, UnsupportedEncodingException {
        if(projectUserService.existsUserByEmail(email)){
            model.addAttribute("error","The Email Address is already in use by another user.");
        }else{
            projectUserService.addUser(request, fullName, email);
            model.addAttribute("success","Successfully added the new user and sent an invitation email.");
        }
        return "add_user";
    }

    @PostMapping("/add_many")
    public String inviteMultipleUser(HttpServletRequest request, @RequestParam("rawListUser") String textAreaInput, Model model) throws MessagingException, UnsupportedEncodingException {
        String message = projectUserService.addMultipleUser(request, textAreaInput);
        model.addAttribute("message", message);
        return "add_multi_user";
    }
}
