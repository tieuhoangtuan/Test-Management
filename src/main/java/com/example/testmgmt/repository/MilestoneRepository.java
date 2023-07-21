package com.example.testmgmt.repository;

import com.example.testmgmt.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    @Query("SELECT ms FROM Milestone ms WHERE ms.project.id = :projectId")
    List<Milestone> getAllByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT ms FROM Milestone ms WHERE ms.isStart = false AND ms.project.id = :projectId AND ms.isComplete != true AND ms.parentMilestone = null order by ms.startDate")
    List<Milestone> getUpcomingMilestones(@Param("projectId") Long projectId);

    @Query("SELECT ms FROM Milestone ms WHERE ms.isStart = true AND ms.project.id = :projectId AND ms.parentMilestone = null order by ms.endDate desc")
    List<Milestone> getActiveMilestones(@Param("projectId") Long projectId);

    @Query("SELECT ms FROM Milestone ms WHERE ms.isComplete = true AND ms.project.id = :projectId AND ms.parentMilestone = null")
    List<Milestone> getCompletedMilestones(@Param("projectId") Long projectId);

    List<Milestone> findAllByProjectId(Long projectId);

    @Query("SELECT ms FROM Milestone ms WHERE ms.project.id = :projectId AND ms.parentMilestone.id = :milestoneId")
    List<Milestone> getSubMilestoneListByParentId(@Param("projectId") Long projectId, @Param("milestoneId") Long milestoneId);
}
