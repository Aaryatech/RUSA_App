package com.ats.rusa_app.model;

public class Institute {

    private Integer mhInstId;
    private String aisheCode;
    private String insName;
    private String district;
    private String taluka;
    private Integer affUniversity;
    private String estYear;
    private String uniName;
    private Integer yesNo;

    public Integer getMhInstId() {
        return mhInstId;
    }

    public void setMhInstId(Integer mhInstId) {
        this.mhInstId = mhInstId;
    }

    public String getAisheCode() {
        return aisheCode;
    }

    public void setAisheCode(String aisheCode) {
        this.aisheCode = aisheCode;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public Integer getAffUniversity() {
        return affUniversity;
    }

    public void setAffUniversity(Integer affUniversity) {
        this.affUniversity = affUniversity;
    }

    public String getEstYear() {
        return estYear;
    }

    public void setEstYear(String estYear) {
        this.estYear = estYear;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public Integer getYesNo() {
        return yesNo;
    }

    public void setYesNo(Integer yesNo) {
        this.yesNo = yesNo;
    }

    @Override
    public String toString() {
        return "Institute{" +
                "mhInstId=" + mhInstId +
                ", aisheCode='" + aisheCode + '\'' +
                ", insName='" + insName + '\'' +
                ", district='" + district + '\'' +
                ", taluka='" + taluka + '\'' +
                ", affUniversity=" + affUniversity +
                ", estYear='" + estYear + '\'' +
                ", uniName='" + uniName + '\'' +
                ", yesNo=" + yesNo +
                '}';
    }
}
