package com.ats.rusa_app.model;

public class Registration {

    private int regId;


    private String userUuid;


    private int userType;


    private String emails;


    private String alternateEmail;


    private String userPassword;


    private String name;


    private String aisheCode;


    private String collegeName;


    private String unversityName;


    private String designationName;


    private String departmentName;

    private String mobileNumber;


    private String authorizedPerson;


    private String dob;


    private String imageName;


    private String tokenId;


    private String registerVia;


    private int isActive;


    private int delStatus;


    private String addDate;


    private String editDate;

//	@Column(name="added_by_user_id")
//	private int addedByUserId;


    private int editByUserId;


    private int exInt1;

    private int exInt2;


    private String exVar1;


    private String exVar2;


    private String emailCode;


    private int emailVerified;


    private String smsCode;


    private int smsVerified;


    private int editByAdminuserId;

    public Registration(int regId, String userUuid, int userType, String emails, String alternateEmail, String userPassword, String name, String aisheCode, String collegeName, String unversityName, String designationName, String departmentName, String mobileNumber, String authorizedPerson, String dob, String imageName, String tokenId, String registerVia, int isActive, int delStatus, String addDate, String editDate, int editByUserId, int exInt1, int exInt2, String exVar1, String exVar2, String emailCode, int emailVerified, String smsCode, int smsVerified, int editByAdminuserId) {
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

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
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

    public int getEditByUserId() {
        return editByUserId;
    }

    public void setEditByUserId(int editByUserId) {
        this.editByUserId = editByUserId;
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

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public int getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(int emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public int getSmsVerified() {
        return smsVerified;
    }

    public void setSmsVerified(int smsVerified) {
        this.smsVerified = smsVerified;
    }

    public int getEditByAdminuserId() {
        return editByAdminuserId;
    }

    public void setEditByAdminuserId(int editByAdminuserId) {
        this.editByAdminuserId = editByAdminuserId;
    }

    @Override
    public String toString() {
        return "Registration{" +
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
                '}';
    }
}
