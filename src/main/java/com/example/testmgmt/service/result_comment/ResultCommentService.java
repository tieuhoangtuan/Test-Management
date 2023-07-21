package com.example.testmgmt.service.result_comment;

import com.example.testmgmt.dto.ResultCommentResponse;

import java.util.List;

public interface ResultCommentService {
    List<ResultCommentResponse> getResultAndCommentSortedByDate(Long testRunUserId);
}
