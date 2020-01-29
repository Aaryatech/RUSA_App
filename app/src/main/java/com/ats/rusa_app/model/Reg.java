package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reg {

    @SerializedName("regId")
    @Expose
    private Integer regId;
    @SerializedName("userUuid")
    @Expose
    private String userUuid;
    @SerializedName("userType")
    @Expose
    private Integer userType;
    @SerializedName("emails")
    @Expose
    private String emails;
    @SerializedName("alternateEmail")
    @Expose
    private String alternateEmail;
    @SerializedName("userPassword")
    @Expose
    private String userPassword;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("aisheCode")
    @Expose
    private String aisheCode;
    @SerializedName("collegeName")
    @Expose
    private String collegeName;
    @SerializedName("unversityName")
    @Expose
    private String unversityName;
    @SerializedName("designationName")
    @Expose
    private String designationName;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("authorizedPerson")
    @Expose
    private String authorizedPerson;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("imageName")
    @Expose
    private String imageName;
    @SerializedName("tokenId")
    @Expose
    private String tokenId;
    @SerializedName("registerVia")
    @Expose
    private String registerVia;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("addDate")
    @Expose
    private String addDate;
    @SerializedName("editDate")
    @Expose
    private String editDate;
    @SerializedName("editByUserId")
    @Expose
    private Integer editByUserId;
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
    @SerializedName("emailCode")
    @Expose
    private String emailCode;
    @SerializedName("emailVerified")
    @Expose
    private Integer emailVerified;
    @SerializedName("smsCode")
    @Expose
    private String smsCode;
    @SerializedName("smsVerified")
    @Expose
    private Integer smsVerified;
    @SerializedName("editByAdminuserId")
    @Expose
    private int editByAdminuserId;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Reg(int regId, String userUuid, int userType, String emails, String alternateEmail, String userPassword, String name, String aisheCode, String collegeName, String unversityName, String designationName, String departmentName, String mobileNumber, String authorizedPerson, String dob, String imageName, String tokenId, String registerVia, int isActive, int delStatus, String addDate, String editDate, int editByUserId, int exInt1, int exInt2, String exVar1, String exVar2, String emailCode, int emailVerified, String smsCode, int smsVerified, int editByAdminuserId) {
        this.regId = regId;
        this.userUuid = userUuid;
        this.userType = userType;
        this.emails = emails;
        this.alternateEmail = alternateEmail;
        this.userPassword = userPassword;
        this.name = name;
        this.aisheCode = aisheCode;
        this.collegeName = collegeName;
        this.unversityName = unversityName;
        this.designationName = designationName;
        this.departmentName = departmentName;
        this.mobileNumber = mobileNumber;
        this.authorizedPerson = authorizedPerson;
        this.dob = dob;
        this.imageName = imageName;
        this.tokenId = tokenId;
        this.registerVia = registerVia;
        this.isActive = isActive;
        this.delStatus = delStatus;
        this.addDate = addDate;
        this.editDate = editDate;
        this.editByUserId = editByUserId;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.emailCode = emailCode;
        this.emailVerified = emailVerified;
        this.smsCode = smsCode;
        this.smsVerified = smsVerified;
        this.editByAdminuserId = editByAdminuserId;
    }




    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAisheCode() {
        return aisheCode;
    }

    public void setAisheCode(String aisheCode) {
        this.aisheCode = aisheCode;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getUnversityName() {
        return unversityName;
    }

    public void setUnversityName(String unversityName) {
        this.unversityName = unversityName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAuthorizedPerson() {
        return authorizedPerson;
    }

    public void setAuthorizedPerson(String authorizedPerson) {
        this.authorizedPerson = authorizedPerson;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getRegisterVia() {
        return registerVia;
    }

    public void setRegisterVia(String registerVia) {
        this.registerVia = registerVia;
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

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

    public Integer getEditByUserId() {
        return editByUserId;
    }

    public void setEditByUserId(Integer editByUserId) {
        this.editByUserId = editByUserId;
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

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public Integer getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getSmsVerified() {
        return smsVerified;
    }

    public void setSmsVerified(Integer smsVerified) {
        this.smsVerified = smsVerified;
    }

    public int getEditByAdminuserId() {
        return editByAdminuserId;
    }

    public void setEditByAdminuserId(int editByAdminuserId) {
        this.editByAdminuserId = editByAdminuserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Reg{" +
                "regId=" + regId +
                ", userUuid='" + userUuid + '\'' +
                ", userType=" + userType +
                ", emails='" + emails + '\'' +
                ", alternateEmail='" + alternateEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", name='" + name + '\'' +
                ", aisheCode='" + aisheCode + '\'' +
                ", collegeName='" + collegeName + '\'' +
                ", unversityName='" + unversityName + '\'' +
                ", designationName='" + designationName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", authorizedPerson='" + authorizedPerson + '\'' +
                ", dob='" + dob + '\'' +
                ", imageName='" + imageName + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", registerVia='" + registerVia + '\'' +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", addDate='" + addDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", editByUserId=" + editByUserId +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", emailCode='" + emailCode + '\'' +
                ", emailVerified=" + emailVerified +
                ", smsCode='" + smsCode + '\'' +
                ", smsVerified=" + smsVerified +
                ", editByAdminuserId=" + editByAdminuserId +
                ", msg='" + msg + '\'' +
                ", error=" + error +
                '}';
    }
}
