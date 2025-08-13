package com.ohgiraffers.section01.model.dto;

public class ResolutionTypeDTO {

    private int resolutionType;
    private String description;

    public ResolutionTypeDTO() {}

    public ResolutionTypeDTO(int resolutionType, String description) {
        this.resolutionType = resolutionType;
        this.description = description;
    }

    public ResolutionTypeDTO(String description) {
        this.description = description;
    }

    public void setResolutionType(int resolutionType) {
        this.resolutionType = resolutionType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResolutionType() {
        return resolutionType;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ResolutionType{" +
                "resolutionType=" + resolutionType +
                ", description='" + description + '\'' +
                '}';
    }
}
