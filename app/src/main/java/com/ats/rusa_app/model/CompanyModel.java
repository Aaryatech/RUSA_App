package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cateGroup")
    @Expose
    private Object cateGroup;
    @SerializedName("titleName")
    @Expose
    private String titleName;
    @SerializedName("urlLink")
    @Expose
    private String urlLink;
    @SerializedName("sliderImage")
    @Expose
    private String sliderImage;
    @SerializedName("sortOrder")
    @Expose
    private Integer sortOrder;
    @SerializedName("addDate")
    @Expose
    private String addDate;
    @SerializedName("editDate")
    @Expose
    private String editDate;
    @SerializedName("addedByUserId")
    @Expose
    private Integer addedByUserId;
    @SerializedName("editByUserId")
    @Expose
    private Integer editByUserId;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("int1")
    @Expose
    private Integer int1;
    @SerializedName("int2")
    @Expose
    private Integer int2;
    @SerializedName("var1")
    @Expose
    private Object var1;
    @SerializedName("var2")
    @Expose
    private Object var2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getCateGroup() {
        return cateGroup;
    }

    public void setCateGroup(Object cateGroup) {
        this.cateGroup = cateGroup;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(String sliderImage) {
        this.sliderImage = sliderImage;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Integer getInt2() {
        return int2;
    }

    public void setInt2(Integer int2) {
        this.int2 = int2;
    }

    public Object getVar1() {
        return var1;
    }

    public void setVar1(Object var1) {
        this.var1 = var1;
    }

    public Object getVar2() {
        return var2;
    }

    public void setVar2(Object var2) {
        this.var2 = var2;
    }

    @Override
    public String toString() {
        return "CompanyModel{" +
                "id=" + id +
                ", cateGroup=" + cateGroup +
                ", titleName='" + titleName + '\'' +
                ", urlLink='" + urlLink + '\'' +
                ", sliderImage='" + sliderImage + '\'' +
                ", sortOrder=" + sortOrder +
                ", addDate='" + addDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", int1=" + int1 +
                ", int2=" + int2 +
                ", var1=" + var1 +
                ", var2=" + var2 +
                '}';
    }
}
