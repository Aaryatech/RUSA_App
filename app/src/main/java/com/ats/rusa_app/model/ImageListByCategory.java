package com.ats.rusa_app.model;

public class ImageListByCategory {

    private int galleryDetailsId;
    private int galleryCatId;
    private int totalCount;
    private int picCount;
    private int videoCount;
    private String cateName;
    private String fileName;

    public int getGalleryDetailsId() {
        return galleryDetailsId;
    }

    public void setGalleryDetailsId(int galleryDetailsId) {
        this.galleryDetailsId = galleryDetailsId;
    }

    public int getGalleryCatId() {
        return galleryCatId;
    }

    public void setGalleryCatId(int galleryCatId) {
        this.galleryCatId = galleryCatId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPicCount() {
        return picCount;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ImageListByCategory{" +
                "galleryDetailsId=" + galleryDetailsId +
                ", galleryCatId=" + galleryCatId +
                ", totalCount=" + totalCount +
                ", picCount=" + picCount +
                ", videoCount=" + videoCount +
                ", cateName='" + cateName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
