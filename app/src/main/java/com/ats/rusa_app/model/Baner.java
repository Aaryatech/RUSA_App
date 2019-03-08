package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Baner {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cateId")
    @Expose
    private Integer cateId;
    @SerializedName("sliderName")
    @Expose
    private String sliderName;
    @SerializedName("urlLink")
    @Expose
    private String urlLink;
    @SerializedName("linkName")
    @Expose
    private String linkName;
    @SerializedName("text1")
    @Expose
    private String text1;
    @SerializedName("text2")
    @Expose
    private String text2;
    @SerializedName("text3")
    @Expose
    private Object text3;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Object getText3() {
        return text3;
    }

    public void setText3(Object text3) {
        this.text3 = text3;
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

    @Override
    public String toString() {
        return "Baner{" +
                "id=" + id +
                ", cateId=" + cateId +
                ", sliderName='" + sliderName + '\'' +
                ", urlLink='" + urlLink + '\'' +
                ", linkName='" + linkName + '\'' +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3=" + text3 +
                ", sliderImage='" + sliderImage + '\'' +
                ", sortOrder=" + sortOrder +
                ", addDate='" + addDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                '}';
    }
}
