package com.ohgiraffers.section01.model.dto;

import java.util.Date;

public class ResolvedIncidentsDTO {

    private int resolutionSeq;
    private Date resolvedTimestamp;
    private char isPushNotified;
    private Date pushNotificationTimestamp;
    private int resolutionTypeId;
    private int incidentSeq;

    public ResolvedIncidentsDTO() {}

    public ResolvedIncidentsDTO(int resolutionSeq, Date resolvedTimestamp, char isPushNotified, Date pushNotificationTimestamp, int resolutionTypeId, int incidentSeq) {
        this.resolutionSeq = resolutionSeq;
        this.resolvedTimestamp = resolvedTimestamp;
        this.isPushNotified = isPushNotified;
        this.pushNotificationTimestamp = pushNotificationTimestamp;
        this.resolutionTypeId = resolutionTypeId;
        this.incidentSeq = incidentSeq;
    }

    public ResolvedIncidentsDTO(Date resolvedTimestamp, char isPushNotified, Date pushNotificationTimestamp, int resolutionTypeId, int incidentSeq) {
        this.resolvedTimestamp = resolvedTimestamp;
        this.isPushNotified = isPushNotified;
        this.pushNotificationTimestamp = pushNotificationTimestamp;
        this.resolutionTypeId = resolutionTypeId;
        this.incidentSeq = incidentSeq;
    }

    public void setResolutionSeq(int resolutionSeq) {
        this.resolutionSeq = resolutionSeq;
    }

    public void setResolvedTimestamp(Date resolvedTimestamp) {
        this.resolvedTimestamp = resolvedTimestamp;
    }

    public void setIsPushNotified(char isPushNotified) {
        this.isPushNotified = isPushNotified;
    }

    public void setPushNotificationTimestamp(Date pushNotificationTimestamp) {
        this.pushNotificationTimestamp = pushNotificationTimestamp;
    }

    public void setResolutionTypeId(int resolutionTypeId) {
        this.resolutionTypeId = resolutionTypeId;
    }

    public void setIncidentSeq(int incidentSeq) {
        this.incidentSeq = incidentSeq;
    }

    public int getResolutionSeq() {
        return resolutionSeq;
    }

    public Date getResolvedTimestamp() {
        return resolvedTimestamp;
    }

    public char getIsPushNotified() {
        return isPushNotified;
    }

    public Date getPushNotificationTimestamp() {
        return pushNotificationTimestamp;
    }

    public int getResolutionTypeId() {
        return resolutionTypeId;
    }

    public int getIncidentSeq() {
        return incidentSeq;
    }

    @Override
    public String toString() {
        return "ResolvedIncidentsDTO{" +
                "resolutionSeq=" + resolutionSeq +
                ", resolvedTimestamp=" + resolvedTimestamp +
                ", isPushNotified=" + isPushNotified +
                ", pushNotificationTimestamp=" + pushNotificationTimestamp +
                ", resolutionTypeId=" + resolutionTypeId +
                ", incidentSeq=" + incidentSeq +
                '}';
    }
}
