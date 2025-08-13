package com.ohgiraffers.section01.model.dto;

import java.util.Date;

public class IncidentHistoryDTO {

    private int incidentSeq;
    private Date incidentTimestamp;
    private int perpetratorId;
    private int victimId;
    private int damageScore;
    private int damageTypeId;

    public IncidentHistoryDTO() {}

    public IncidentHistoryDTO(int incidentSeq, Date incidentTimestamp, int perpetratorId, int victimId, int damageScore, int damageTypeId) {
        this.incidentSeq = incidentSeq;
        this.incidentTimestamp = incidentTimestamp;
        this.perpetratorId = perpetratorId;
        this.victimId = victimId;
        this.damageScore = damageScore;
        this.damageTypeId = damageTypeId;
    }

    public IncidentHistoryDTO(Date incidentTimeStamp, int perpetratorId, int victimId, int damageScore, int damageTypeId) {
        this.incidentTimestamp = incidentTimeStamp;
        this.perpetratorId = perpetratorId;
        this.victimId = victimId;
        this.damageScore = damageScore;
        this.damageTypeId = damageTypeId;
    }

    public void setIncidentSeq(int incidentSeq) {
        this.incidentSeq = incidentSeq;
    }

    public void setIncidentTimeStamp(Date incidentTimeStamp) {
        this.incidentTimestamp = incidentTimeStamp;
    }

    public void setPerpetratorId(int perpetratorId) {
        this.perpetratorId = perpetratorId;
    }

    public void setVictimId(int victimId) {
        this.victimId = victimId;
    }

    public void setDamageScore(int damageScore) {
        this.damageScore = damageScore;
    }

    public void setDamageTypeId(int damageTypeId) {
        this.damageTypeId = damageTypeId;
    }

    public int getIncidentSeq() {
        return incidentSeq;
    }

    public Date getIncidentTimestamp() {
        return incidentTimestamp;
    }

    public int getPerpetratorId() {
        return perpetratorId;
    }

    public int getVictimId() {
        return victimId;
    }

    public int getDamageScore() {
        return damageScore;
    }

    public int getDamageTypeId() {
        return damageTypeId;
    }

    @Override
    public String toString() {
        return "IncidentHistoryDTO{" +
                "incidentSeq=" + incidentSeq +
                ", incidentTimeStamp=" + incidentTimestamp +
                ", perpetratorId=" + perpetratorId +
                ", victimId=" + victimId +
                ", damageScore=" + damageScore +
                ", damageTypeId=" + damageTypeId +
                '}';
    }
}
