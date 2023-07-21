package com.example.testmgmt.service.section;
import com.example.testmgmt.dto.SectionDto;
import com.example.testmgmt.entity.Project;
import com.example.testmgmt.entity.Section;
import com.example.testmgmt.repository.ProjectRepository;
import com.example.testmgmt.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class SectionServiceImpl implements SectionService{
    private final SectionRepository sectionRepository;
    private final ProjectRepository projectRepository;

    public SectionServiceImpl(SectionRepository sectionRepository, ProjectRepository projectRepository){
        this.sectionRepository = sectionRepository;
        this.projectRepository = projectRepository;
    }
    @Override
    public void addSection(SectionDto sectionDto, Long projectId) {
        Project existProject = projectRepository.getProjectById(projectId);
        Section newSection = new Section();
        newSection.setName(sectionDto.getName());
        newSection.setDescription(sectionDto.getDescription());
        newSection.setProject(existProject);
        if(sectionDto.getParentSectionId() != 0)
            newSection.setParentSection(sectionRepository.getSectionById(sectionDto.getParentSectionId()));

        sectionRepository.save(newSection);
    }

    @Override
    public void editSection(SectionDto sectionDto) {
        Section existSection = sectionRepository.getSectionById(sectionDto.getId());
        if(sectionDto.getName() != null)
            existSection.setName(sectionDto.getName());
        if(sectionDto.getDescription() != null)
            existSection.setDescription(sectionDto.getDescription());
        sectionRepository.save(existSection);
    }

    @Override
    public void deleteSection(Long sectionId) {
        sectionRepository.deleteById(sectionId);
    }

    @Override
    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);

    }

    @Override
    public List<Section> getAllSectionsByProjectId(Long projectId) {
        return sectionRepository.findAllByProjectId(projectId);
    }

    @Override
    public Section getSectionById(String id) {
        return sectionRepository.findById(id);
    }

    @Override
    public List<Section> getSectionsByProject(Long projectId) {
        return sectionRepository.getAllByParentSectionByProjectId(projectId);
    }

    public List<Section> findAllById(List<Long> ids) {
        List<Section> sections = new ArrayList<>();
        for (Long id : ids) {
            Optional<Section> section = sectionRepository.findById(id);
            section.ifPresent(sections::add);
        }
        return sections;
    }
}
