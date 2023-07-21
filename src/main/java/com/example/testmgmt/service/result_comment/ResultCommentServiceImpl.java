package com.example.testmgmt.service.result_comment;

import com.example.testmgmt.dto.ResultComment;
import com.example.testmgmt.dto.ResultCommentResponse;
import com.example.testmgmt.entity.User;
import com.example.testmgmt.repository.ResultCommentRepository;
import com.example.testmgmt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ResultCommentServiceImpl implements ResultCommentService{
    private final ResultCommentRepository resultCommentRepository;
    private final UserRepository userRepository;

    public ResultCommentServiceImpl(ResultCommentRepository resultCommentRepository, UserRepository userRepository) {
        this.resultCommentRepository = resultCommentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ResultCommentResponse> getResultAndCommentSortedByDate(Long testRunUserId) {
        ArrayList resultCommentList = (ArrayList)resultCommentRepository.getResultAndCommentSortedByDate(testRunUserId);
        return convertToList(resultCommentList);
    }
    private ResultCommentResponse mapToResultCommentResponse(ResultComment resultComment){
        return new ResultCommentResponse()
                .setId(resultComment.getId())
                .setTableName(resultComment.getTable_name())
                .setStatus(resultComment.getStatus())
                .setComment(resultComment.getComment())
                .setCreatedDate(timeToString(resultComment.getCreated_date()))
                .setUserId(userRepository.findById(resultComment.getUser_id()).orElseThrow())
                .setAssignUserId(userRepository.findById(resultComment.getAssign_user_Id()).orElseThrow())
                .setTestRunUserId(resultComment.getTest_run_user_id());
    }

    private List<ResultCommentResponse> convertToList(List<Object[]> nativeData){
        List<ResultCommentResponse> list = new ArrayList<>();
        for (Object[] objArray : nativeData){
            ResultCommentResponse response = new ResultCommentResponse();
            User existedUser = userRepository.findById((Long) objArray[6]).orElseThrow();
            User assignedUser = new User();
            if(objArray[8] == null){
                assignedUser = null;
            } else {
                assignedUser = userRepository.findById((Long) objArray[8]).orElse(null);
            }
            Timestamp timestamp = (Timestamp) objArray[5];
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            String dateFormat =timeToString(localDateTime);

            response
                    .setTableName((String) objArray[0])
                    .setId((Long) objArray[1])
                    .setStatus((String) objArray[2])
                    .setComment((String) objArray[3])
                    .setElapsedTime((String) objArray[4])
                    .setCreatedDate(dateFormat)
                    .setUserId(existedUser)
                    .setTestRunUserId((Long) objArray[7])
                    .setAssignUserId(assignedUser);

            list.add(response);
        }
        return list;
    }

    private String timeToString(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/dd/yyyy HH:mm a",new Locale("vi", "VN"));
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        return formattedDateTime;
    }
}
