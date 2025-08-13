package com.ohgiraffers.section01.model.dto;

public class MemberTypeDTO {

    private int memberType;
    private String Description;

    public MemberTypeDTO() {}

    // insert, update, delete constructor
    public MemberTypeDTO(String description) {
        Description = description;
    }

    // select constructor
    public MemberTypeDTO(int memberType, String description) {
        this.memberType = memberType;
        Description = description;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getMemberType() {
        return memberType;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public String toString() {
        return "MemberTypeDTO{" +
                "memberType=" + memberType +
                ", Description='" + Description + '\'' +
                '}';
    }
}
