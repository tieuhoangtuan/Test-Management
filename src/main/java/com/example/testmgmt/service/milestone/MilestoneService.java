package com.example.testmgmt.service.milestone;

import com.example.testmgmt.data_container.StatusCounts;
import com.example.testmgmt.dto.MilestoneDto;
import com.example.testmgmt.entity.Milestone;

import java.text.ParseException;
import java.util.List;

public interface MilestoneService {
    void addMilestone(MilestoneDto milestoneDto) throws ParseException;
    void deleteMilestone(Long milestoneId);
    void editMilestone(Long milestoneId, MilestoneDto milestoneDto) throws ParseException;
    void startMilestone(Long milestoneId, String startDate, String endDate) throws ParseException;
    List<Milestone> getAllMilestonesByProjectId(Long projectId);
    List<Milestone> getUpcomingMilestones(Long projectId);
    List<Milestone> getActiveMilestones(Long projectId);
    List<Milestone> getCompletedMilestones(Long projectId);
    Milestone getMilestoneById(Long milestoneId);
    StatusCounts getStatusCountsByMilestone(Long milestoneId);
    List<Milestone> getAllMilestoneByProjectId(Long projectId);
    MilestoneDto getMileDtoByMilestoneId(Long milestoneId);

    List<Milestone> getSubMilestoneListByParentId(Long projectId, Long milestoneId);
}
