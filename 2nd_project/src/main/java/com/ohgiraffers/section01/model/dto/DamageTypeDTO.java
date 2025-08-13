package com.ohgiraffers.section01.model.dto;

import java.util.Objects;

public class DamageTypeDTO {

    private int damageTypeId;
    private String penalty;
    private int score;
    private int memberType;

    public DamageTypeDTO() {}

    public DamageTypeDTO(int damageTypeId, String penalty, int score, int memberType) {
        this.damageTypeId = damageTypeId;
        this.penalty = penalty;
        this.score = score;
        this.memberType = memberType;
    }

    public DamageTypeDTO(String penalty, int score, int memberType) {
        this.penalty = penalty;
        this.score = score;
        this.memberType = memberType;
    }

    public void setDamageTypeId(int damageTypeId) {
        this.damageTypeId = damageTypeId;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public int getDamageTypeId() {
        return damageTypeId;
    }

    public String getPenalty() {
        return penalty;
    }

    public int getScore() {
        return score;
    }

    public int getMemberType() {
        return memberType;
    }

    @Override
    public String toString() {
        String memberTypeString = (memberType == 1)  ? "선생님":"학생";

//        return  " 벌점사유 : '" + penalty + '\'' +
//                ", 벌점 : " + score +
//                ", 대상자구분 : " + memberTypeString
//        ;
//
        return String.format("가해자 : %4s, 벌점 : %2d, 피해유형 : %2d, 벌점사유 : %-20s",
                memberTypeString, score, damageTypeId, penalty);
    }
}
