package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("catId")
    @Expose
    private Integer catId;
    @SerializedName("catName")
    @Expose
    private String catName;
    @SerializedName("catDesc")
    @Expose
    private String catDesc;
    @SerializedName("imgCount")
    @Expose
    private Integer imgCount;
    @SerializedName("videoCount")
    @Expose
    private Integer videoCount;
    @SerializedName("categoryCount")
    @Expose
    private Integer categoryCount;
    @SerializedName("pageSlug")
    @Expose
    private String pageSlug;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public Integer getImgCount() {
        return imgCount;
    }

    public void setImgCount(Integer imgCount) {
        this.imgCount = imgCount;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getPageSlug() {
        return pageSlug;
    }

    public void setPageSlug(String pageSlug) {
        this.pageSlug = pageSlug;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                ", catDesc='" + catDesc + '\'' +
                ", imgCount=" + imgCount +
                ", videoCount=" + videoCount +
                ", categoryCount=" + categoryCount +
                ", pageSlug='" + pageSlug + '\'' +
                '}';
    }
}
