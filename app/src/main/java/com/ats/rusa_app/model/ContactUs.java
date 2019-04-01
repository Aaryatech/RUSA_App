package com.ats.rusa_app.model;

public class ContactUs {
    private int id;

    private String contactName;

    private String emailId;

    private String mobileNo;

    private String message;

    private String topic;

    private String userAgent;

    private String addDate;

    private int status;


    private String remark;

    private int statusByAdmin;
    private int delStatus;

    private String ipAddress;

    private int exInt1;

    private int exInt2;

    private String exVar1;

    private String exVar2;

    public ContactUs(int id, String contactName, String emailId, String mobileNo, String message, String topic, String userAgent, String addDate, int status, String remark, int statusByAdmin, int delStatus, String ipAddress, int exInt1, int exInt2, String exVar1, String exVar2) {
        this.id = id;
        this.contactName = contactName;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.message = message;
        this.topic = topic;
        this.userAgent = userAgent;
        this.addDate = addDate;
        this.status = status;
        this.remark = remark;
        this.statusByAdmin = statusByAdmin;
        this.delStatus = delStatus;
        this.ipAddress = ipAddress;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatusByAdmin() {
        return statusByAdmin;
    }

    public void setStatusByAdmin(int statusByAdmin) {
        this.statusByAdmin = statusByAdmin;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
        return "ContactUs{" +
                "id=" + id +
                ", contactName='" + contactName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", message='" + message + '\'' +
                ", topic='" + topic + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", addDate='" + addDate + '\'' +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", statusByAdmin=" + statusByAdmin +
                ", delStatus=" + delStatus +
                ", ipAddress='" + ipAddress + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                '}';
    }
}
