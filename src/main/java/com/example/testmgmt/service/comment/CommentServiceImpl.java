package com.example.testmgmt.service.comment;

import com.example.testmgmt.dto.CommentDto;
import com.example.testmgmt.entity.Comment;
import com.example.testmgmt.entity.TestRunUser;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.CommentRepository;
import com.example.testmgmt.repository.TestRunUserRepository;
import com.example.testmgmt.repository.UserRepository;
import com.example.testmgmt.service.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TestRunUserRepository testRunUserRepository;
    private final UserService userService;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, TestRunUserRepository testRunUserRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.testRunUserRepository = testRunUserRepository;
        this.userService = userService;
    }

    @Override
    public void AddComment(CommentDto commentDto, Long testRunUserId) {
        Comment newComment = new Comment();
        if(commentDto.getAssignUserId() != null){
            newComment.setStatus("Assigned");
            User assignedUser = userRepository.findById(commentDto.getAssignUserId()).orElse(null);
            newComment.setAssignUser(assignedUser);
            TestRunUser existedTestRunUser = testRunUserRepository.findById(testRunUserId).orElseThrow();
            existedTestRunUser.setAssignToUser(assignedUser);
        } else {
            newComment.setStatus("Comment");
        }
        newComment.setUser(userService.getCurrentUser());
        newComment.setCreatedDate(LocalDateTime.now());
        newComment.setComment(commentDto.getComment());
        newComment.setTestRunUser(testRunUserRepository.findById(testRunUserId).orElseThrow());
        commentRepository.save(newComment);
    }
}
