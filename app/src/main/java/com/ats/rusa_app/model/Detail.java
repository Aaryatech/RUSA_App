package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {

    @SerializedName("logoData")
    @Expose
    private LogoData logoData;
    @SerializedName("baner")
    @Expose
    private Baner baner;
    @SerializedName("videoList")
    @Expose
    private List<VideoList> videoList = null;
    @SerializedName("photoList")
    @Expose
    private List<PhotoList> photoList = null;
    @SerializedName("testimonialList")
    @Expose
    private List<TestImonialList> testimonialList = null;
    @SerializedName("newsList")
    @Expose
    private List<NewDetail> newsList = null;
    @SerializedName("socialList")
    @Expose
    private List<Object> socialList = null;
    @SerializedName("cmsList")
    @Expose
    private List<CmsList> cmsList = null;

    private List<CompanyModel> companyList = null;

    public LogoData getLogoData() {
        return logoData;
    }

    public void setLogoData(LogoData logoData) {
        this.logoData = logoData;
    }

    public Baner getBaner() {
        return baner;
    }

    public void setBaner(Baner baner) {
        this.baner = baner;
    }

    public List<VideoList> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoList> videoList) {
        this.videoList = videoList;
    }

    public List<PhotoList> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PhotoList> photoList) {
        this.photoList = photoList;
    }

    public List<TestImonialList> getTestimonialList() {
        return testimonialList;
    }

    public void setTestimonialList(List<TestImonialList> testimonialList) {
        this.testimonialList = testimonialList;
    }

    public List<NewDetail> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewDetail> newsList) {
        this.newsList = newsList;
    }

    public List<Object> getSocialList() {
        return socialList;
    }

    public void setSocialList(List<Object> socialList) {
        this.socialList = socialList;
    }

    public List<CmsList> getCmsList() {
        return cmsList;
    }

    public void setCmsList(List<CmsList> cmsList) {
        this.cmsList = cmsList;
    }

    public List<CompanyModel> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<CompanyModel> companyList) {
        this.companyList = companyList;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "logoData=" + logoData +
                ", baner=" + baner +
                ", videoList=" + videoList +
                ", photoList=" + photoList +
                ", testimonialList=" + testimonialList +
                ", newsList=" + newsList +
                ", socialList=" + socialList +
                ", cmsList=" + cmsList +
                '}';
    }
}
