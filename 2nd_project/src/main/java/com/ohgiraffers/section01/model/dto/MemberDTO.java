package com.ohgiraffers.section01.model.dto;

public class MemberDTO {

    private int memberId;
    private String name;
    private String phoneNumber;
    private int memberType;

    public MemberDTO() {}

    public MemberDTO(int memberId, String name, String phoneNumber, int memberType) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.memberType = memberType;
    }

    public MemberDTO(String name, String phoneNumber, int memberType) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.memberType = memberType;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getMemberType() {
        return memberType;
    }

    @Override
    public String toString() {
        String memberTypeString = (memberType == 1)  ? "선생님":"학생";

        return  String.format("회원번호 : %2d, 회원명 : %-20s, 회원구분 : %2s", memberId, name, memberTypeString);
    }
}
