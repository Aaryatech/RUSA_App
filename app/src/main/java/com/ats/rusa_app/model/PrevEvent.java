package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrevEvent {
    @SerializedName("newsblogsId")
    @Expose
    private Integer newsblogsId;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("descriptions")
    @Expose
    private String descriptions;
    @SerializedName("languageId")
    @Expose
    private Integer languageId;
    @SerializedName("featuredImage")
    @Expose
    private String featuredImage;
    @SerializedName("downloadPdf")
    @Expose
    private String downloadPdf;
    @SerializedName("apply")
    @Expose
    private Integer apply;
    @SerializedName("isFeedback")
    @Expose
    private Integer isFeedback;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getNewsblogsId() {
        return newsblogsId;
    }

    public void setNewsblogsId(Integer newsblogsId) {
        this.newsblogsId = newsblogsId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getDownloadPdf() {
        return downloadPdf;
    }

    public void setDownloadPdf(String downloadPdf) {
        this.downloadPdf = downloadPdf;
    }

    public Integer getApply() {
        return apply;
    }

    public void setApply(Integer apply) {
        this.apply = apply;
    }

    public Integer getIsFeedback() {
        return isFeedback;
    }

    public void setIsFeedback(Integer isFeedback) {
        this.isFeedback = isFeedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PrevEvent{" +
                "newsblogsId=" + newsblogsId +
                ", heading='" + heading + '\'' +
                ", descriptions='" + descriptions + '\'' +
                ", languageId=" + languageId +
                ", featuredImage='" + featuredImage + '\'' +
                ", downloadPdf='" + downloadPdf + '\'' +
                ", apply=" + apply +
                ", isFeedback=" + isFeedback +
                ", date='" + date + '\'' +
                '}';
    }
}
