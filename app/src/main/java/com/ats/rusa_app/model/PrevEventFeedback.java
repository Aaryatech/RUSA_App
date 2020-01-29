package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrevEventFeedback {
    @SerializedName("eventRegId")
    @Expose
    private Integer eventRegId;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("regDate")
    @Expose
    private String regDate;
    @SerializedName("newsblogsId")
    @Expose
    private Integer newsblogsId;
    @SerializedName("statusApproval")
    @Expose
    private Integer statusApproval;
    @SerializedName("approvalDate")
    @Expose
    private Object approvalDate;
    @SerializedName("approveBy")
    @Expose
    private Integer approveBy;
    @SerializedName("doc1")
    @Expose
    private String doc1;
    @SerializedName("doc2")
    @Expose
    private Integer doc2;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Integer getEventRegId() {
        return eventRegId;
    }

    public void setEventRegId(Integer eventRegId) {
        this.eventRegId = eventRegId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Integer getNewsblogsId() {
        return newsblogsId;
    }

    public void setNewsblogsId(Integer newsblogsId) {
        this.newsblogsId = newsblogsId;
    }

    public Integer getStatusApproval() {
        return statusApproval;
    }

    public void setStatusApproval(Integer statusApproval) {
        this.statusApproval = statusApproval;
    }

    public Object getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Object approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Integer getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(Integer approveBy) {
        this.approveBy = approveBy;
    }

    public String getDoc1() {
        return doc1;
    }

    public void setDoc1(String doc1) {
        this.doc1 = doc1;
    }

    public Integer getDoc2() {
        return doc2;
    }

    public void setDoc2(Integer doc2) {
        this.doc2 = doc2;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PrevEventFeedback{" +
                "eventRegId=" + eventRegId +
                ", userId=" + userId +
                ", regDate='" + regDate + '\'' +
                ", newsblogsId=" + newsblogsId +
                ", statusApproval=" + statusApproval +
                ", approvalDate=" + approvalDate +
                ", approveBy=" + approveBy +
                ", doc1='" + doc1 + '\'' +
                ", doc2=" + doc2 +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
