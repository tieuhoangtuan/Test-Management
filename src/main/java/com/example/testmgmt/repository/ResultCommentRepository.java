package com.example.testmgmt.repository;

import com.example.testmgmt.dto.ResultComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class ResultCommentRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> getResultAndCommentSortedByDate(Long testRunUserId) {
        Query query = entityManager.createNativeQuery("select 'test_result' As table_name, id, status, comment, elapsed_time, created_date, user_id, test_run_user_id, assign_user_id " +
                "from test_result " +
                "where test_run_user_id = ?1 "+
                "UNION ALL " +
                "select 'comment' as table_name, id, status, comment, NULL AS elapsed_time, created_date, user_id, test_run_user_id, assign_user_id " +
                "from comment " +
                "where test_run_user_id = ?1 order by created_date desc");
        query.setParameter(1, testRunUserId );
        List<Object[]> resultCommentList = query.getResultList();
        return resultCommentList;
    }
}
