package com.example.testmgmt.service.milestone;

import com.example.testmgmt.data_container.StatusCounts;
import com.example.testmgmt.dto.MilestoneDto;
import com.example.testmgmt.entity.Milestone;
import com.example.testmgmt.entity.Project;
import com.example.testmgmt.entity.TestRun;
import com.example.testmgmt.entity.TestRunUser;
import com.example.testmgmt.repository.MilestoneRepository;
import com.example.testmgmt.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    public MilestoneServiceImpl(MilestoneRepository milestoneRepository,
                                ProjectRepository projectRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void addMilestone(MilestoneDto milestoneDto) throws ParseException {
        Milestone newMilestone = new Milestone();
        mapToMilestone(milestoneDto, newMilestone);
        milestoneRepository.save(newMilestone);
    }

    @Override
    public void deleteMilestone(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }

    @Override
    public void editMilestone(Long milestoneId, MilestoneDto milestoneDto) throws ParseException {
        Milestone existedMilestone = milestoneRepository.findById(milestoneId).orElse(null);
        mapToMilestone(milestoneDto, existedMilestone);
        if(milestoneDto.getIsComplete() == true){
            if(!existedMilestone.getSubMilestones().isEmpty()){
                for(Milestone subMilestone : existedMilestone.getSubMilestones()){
                    subMilestone.setComplete(true);
                    subMilestone.setStart(false);
                }
            }
        }
        milestoneRepository.save(existedMilestone);
    }

    @Override
    public void startMilestone(Long milestoneId, String startDate, String endDate) throws ParseException {
        Milestone existedMilestone = milestoneRepository.findById(milestoneId).orElse(null);
        if(startDate != ""){
            existedMilestone.setStartDate(formatDate(startDate));
        } else {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
            String formattedDate = dateFormat.format(currentDate);
            Date parsedDate = dateFormat.parse(formattedDate);
            existedMilestone.setStartDate(parsedDate);
        }
        if (endDate != ""){
            existedMilestone.setEndDate(formatDate(endDate));
        }else {
            existedMilestone.setEndDate(null);
        }
        existedMilestone.setStart(true);
        milestoneRepository.save(existedMilestone);
    }

    @Override
    public List<Milestone> getAllMilestonesByProjectId(Long projectId) {
        return milestoneRepository.getAllByProjectId(projectId);
    }

    @Override
    public List<Milestone> getUpcomingMilestones(Long projectId) {
        List<Milestone> milestoneList = milestoneRepository.getUpcomingMilestones(projectId);
        return milestoneList;
    }

    @Override
    public List<Milestone> getActiveMilestones(Long projectId) {
        List<Milestone> milestoneList = milestoneRepository.getActiveMilestones(projectId);
        return milestoneList;
    }

    @Override
    public List<Milestone> getCompletedMilestones(Long projectId) {
        List<Milestone> milestoneList = milestoneRepository.getCompletedMilestones(projectId);
        return milestoneList;
    }

    @Override
    public Milestone getMilestoneById(Long milestoneId) {
        return milestoneRepository.findById(milestoneId).orElse(null);
    }

    @Override
    public StatusCounts getStatusCountsByMilestone(Long milestoneId) {
        Milestone existedMilestone = milestoneRepository.findById(milestoneId).orElse(null);
        int untestedCount = 0;
        int passedCount = 0;
        int failedCount = 0;
        int blockedCount = 0;
        int retestCount = 0;
        int totalCount = 0;
        List<TestRun> testRuns = existedMilestone.getTestRuns();
        for(TestRun testRun : testRuns){
            for (TestRunUser testRunUser: testRun.getTestRunUsers()){
                String status = testRunUser.getStatus();
                if (status.equals("Passed")) {
                    passedCount++;
                } else if (status.equals("Failed")) {
                    failedCount++;
                } else if (status.equals("Untested")) {
                    untestedCount++;
                } else if (status.equals("Blocked")) {
                    blockedCount++;
                } else if (status.equals("Retest")) {
                    retestCount++;
                }
            }
            totalCount += testRun.getTestRunUsers().size();
        }

        // Store status count in data class
        StatusCounts statusCounts = new StatusCounts();
        statusCounts.setBlockedCount(blockedCount);
        statusCounts.setFailedCount(failedCount);
        statusCounts.setPassedCount(passedCount);
        statusCounts.setRetestCount(retestCount);
        statusCounts.setUntestedCount(untestedCount);
        statusCounts.setTotalCount(totalCount);
        return statusCounts;
    }

    private Date formatDate(String rawDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
        return dateFormat.parse(rawDate);
    }

    private void mapToMilestone(MilestoneDto milestoneDto, Milestone milestone) throws ParseException {
        Project project = projectRepository.findById(milestoneDto.getProjectId()).orElse(null);
        if(milestoneDto.getStartDate() != ""){
            milestone.setStartDate(formatDate(milestoneDto.getStartDate()));
        }
        if (milestoneDto.getEndDate() != ""){
            milestone.setEndDate(formatDate(milestoneDto.getEndDate()));
        }
        if (milestoneDto.getParentMilestoneId() != null){
            milestone.setParentMilestone(milestoneRepository.findById(milestoneDto.getParentMilestoneId()).orElseThrow());
        }
        if(milestoneDto.getStartDate() != "" || milestoneDto.getIsComplete() == true){
            milestone.setStart(false);
        } else {
            milestone.setStart(true);
        }
        milestone.setName(milestoneDto.getName());
        milestone.setDescription(milestoneDto.getDescription());
        milestone.setProject(project);
        milestone.setComplete(milestoneDto.getIsComplete());
    }

    @Override
    public List<Milestone> getAllMilestoneByProjectId(Long projectId) {
        return milestoneRepository.findAllByProjectId(projectId);
    }

    @Override
    public MilestoneDto getMileDtoByMilestoneId(Long milestoneId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
        Milestone existedMile = milestoneRepository.findById(milestoneId).orElse(null);
        MilestoneDto milestoneDto = new MilestoneDto();
        if(existedMile.getStartDate()!=null){
            milestoneDto.setStartDate(sdf.format(existedMile.getStartDate()));
        } else {
            milestoneDto.setStartDate("");
        }
        if (existedMile.getEndDate()!=null){
            milestoneDto.setEndDate(sdf.format(existedMile.getEndDate()));
        }else {
            milestoneDto.setEndDate("");
        }
        return milestoneDto;
    }

    @Override
    public List<Milestone> getSubMilestoneListByParentId(Long projectId, Long milestoneId) {
        List<Milestone> subMilestones = milestoneRepository.getSubMilestoneListByParentId(projectId, milestoneId);
        return subMilestones;
    }
}
