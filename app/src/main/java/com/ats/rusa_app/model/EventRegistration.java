package com.ats.rusa_app.model;

public class EventRegistration {
    private int eventRegId;
    private int userId;
    private String regDate;
    private int newsblogsId;
    private int statusApproval;
    private String approvalDate;
    private int approveBy;
    private String doc1;
    private int doc2;
    private int isActive;
    private int delStatus;
    private int exInt1;
    private int exInt2;
    private String exVar1;
    private String exVar2;

    public EventRegistration(int eventRegId, int userId, String regDate, int newsblogsId, int statusApproval, String approvalDate, int approveBy, String doc1, int doc2, int isActive, int delStatus, int exInt1, int exInt2, String exVar1, String exVar2) {
        this.eventRegId = eventRegId;
        this.userId = userId;
        this.regDate = regDate;
        this.newsblogsId = newsblogsId;
        this.statusApproval = statusApproval;
        this.approvalDate = approvalDate;
        this.approveBy = approveBy;
        this.doc1 = doc1;
        this.doc2 = doc2;
        this.isActive = isActive;
        this.delStatus = delStatus;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
    }

    public int getEventRegId() {
        return eventRegId;
    }

    public void setEventRegId(int eventRegId) {
        this.eventRegId = eventRegId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getNewsblogsId() {
        return newsblogsId;
    }

    public void setNewsblogsId(int newsblogsId) {
        this.newsblogsId = newsblogsId;
    }

    public int getStatusApproval() {
        return statusApproval;
    }

    public void setStatusApproval(int statusApproval) {
        this.statusApproval = statusApproval;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public int getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(int approveBy) {
        this.approveBy = approveBy;
    }

    public String getDoc1() {
        return doc1;
    }

    public void setDoc1(String doc1) {
        this.doc1 = doc1;
    }

    public int getDoc2() {
        return doc2;
    }

    public void setDoc2(int doc2) {
        this.doc2 = doc2;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    @Override
    public String toString() {
        return "EventRegistration{" +
                "eventRegId=" + eventRegId +
                ", userId=" + userId +
                ", regDate='" + regDate + '\'' +
                ", newsblogsId=" + newsblogsId +
                ", statusApproval=" + statusApproval +
                ", approvalDate='" + approvalDate + '\'' +
                ", approveBy=" + approveBy +
                ", doc1='" + doc1 + '\'' +
                ", doc2=" + doc2 +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                '}';
    }
}
