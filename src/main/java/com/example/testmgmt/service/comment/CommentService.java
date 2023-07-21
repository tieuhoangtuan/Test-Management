package com.example.testmgmt.service.comment;

import com.example.testmgmt.dto.CommentDto;

public interface CommentService {
    public void AddComment(CommentDto commentDto, Long testRunUserId);
}
