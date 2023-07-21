package com.example.testmgmt.service.section;

import com.example.testmgmt.dto.SectionDto;
import com.example.testmgmt.entity.Section;
import java.util.List;
import java.util.Optional;

public interface SectionService {
    void addSection(SectionDto sectionDto, Long projectId);
    void editSection(SectionDto sectionDto);
    void deleteSection(Long sectionId);
    Optional<Section> getSectionById(Long id);

    List<Section> getSectionsByProject(Long projectId);

    List<Section> getAllSectionsByProjectId(Long projectId);

    Section getSectionById(String id);

    List<Section> findAllById(List<Long> ids);

}
