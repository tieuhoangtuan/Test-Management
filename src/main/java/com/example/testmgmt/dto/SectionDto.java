package com.example.testmgmt.dto;

public class SectionDto {
    private Long id;
    private String name;
    private String description;
    private Long parentSectionId;

    public SectionDto() {
    }

    public SectionDto(String name, String description, Long parentSectionId) {
        this.name = name;
        this.description = description;
        this.parentSectionId = parentSectionId;
    }

    public SectionDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentSectionId() {
        return parentSectionId;
    }

    public void setParentSectionId(Long parentSectionId) {
        this.parentSectionId = parentSectionId;
    }
}
