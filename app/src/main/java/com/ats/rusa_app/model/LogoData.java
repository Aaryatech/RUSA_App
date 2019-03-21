package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("logoMain")
    @Expose
    private Object logoMain;
    @SerializedName("logo2")
    @Expose
    private Object logo2;
    @SerializedName("logo3")
    @Expose
    private Object logo3;
    @SerializedName("addDate")
    @Expose
    private Object addDate;
    @SerializedName("editDate")
    @Expose
    private Object editDate;
    @SerializedName("addedByUserId")
    @Expose
    private Integer addedByUserId;
    @SerializedName("editByUserId")
    @Expose
    private Integer editByUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getLogoMain() {
        return logoMain;
    }

    public void setLogoMain(Object logoMain) {
        this.logoMain = logoMain;
    }

    public Object getLogo2() {
        return logo2;
    }

    public void setLogo2(Object logo2) {
        this.logo2 = logo2;
    }

    public Object getLogo3() {
        return logo3;
    }

    public void setLogo3(Object logo3) {
        this.logo3 = logo3;
    }

    public Object getAddDate() {
        return addDate;
    }

    public void setAddDate(Object addDate) {
        this.addDate = addDate;
    }

    public Object getEditDate() {
        return editDate;
    }

    public void setEditDate(Object editDate) {
        this.editDate = editDate;
    }

    public Integer getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(Integer addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public Integer getEditByUserId() {
        return editByUserId;
    }

    public void setEditByUserId(Integer editByUserId) {
        this.editByUserId = editByUserId;
    }

    @Override
    public String toString() {
        return "LogoData{" +
                "id=" + id +
                ", logoMain=" + logoMain +
                ", logo2=" + logo2 +
                ", logo3=" + logo3 +
                ", addDate=" + addDate +
                ", editDate=" + editDate +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                '}';
    }
}
