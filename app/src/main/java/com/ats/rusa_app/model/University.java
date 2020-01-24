package com.ats.rusa_app.model;

public class University {

    private Integer uniId;
    private String uniName;
    private Integer delStatus;

    public Integer getUniId() {
        return uniId;
    }

    public void setUniId(Integer uniId) {
        this.uniId = uniId;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "University{" +
                "uniId=" + uniId +
                ", uniName='" + uniName + '\'' +
                ", delStatus=" + delStatus +
                '}';
    }
}
