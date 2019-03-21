package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoList {

    @SerializedName("galleryDetailsId")
    @Expose
    private Integer galleryDetailsId;
    @SerializedName("galleryCatId")
    @Expose
    private Integer galleryCatId;
    @SerializedName("pageId")
    @Expose
    private Integer pageId;
    @SerializedName("sectionId")
    @Expose
    private Integer sectionId;
    @SerializedName("typeVideoImage")
    @Expose
    private String typeVideoImage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fileType")
    @Expose
    private Object fileType;
    @SerializedName("fileSize")
    @Expose
    private Object fileSize;
    @SerializedName("sortNo")
    @Expose
    private Integer sortNo;
    @SerializedName("addDate")
    @Expose
    private String addDate;
    @SerializedName("editDate")
    @Expose
    private Object editDate;
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
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exVar1")
    @Expose
    private Object exVar1;
    @SerializedName("exVar2")
    @Expose
    private Object exVar2;

    public Integer getGalleryDetailsId() {
        return galleryDetailsId;
    }

    public void setGalleryDetailsId(Integer galleryDetailsId) {
        this.galleryDetailsId = galleryDetailsId;
    }

    public Integer getGalleryCatId() {
        return galleryCatId;
    }

    public void setGalleryCatId(Integer galleryCatId) {
        this.galleryCatId = galleryCatId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getTypeVideoImage() {
        return typeVideoImage;
    }

    public void setTypeVideoImage(String typeVideoImage) {
        this.typeVideoImage = typeVideoImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Object getFileType() {
        return fileType;
    }

    public void setFileType(Object fileType) {
        this.fileType = fileType;
    }

    public Object getFileSize() {
        return fileSize;
    }

    public void setFileSize(Object fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
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

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
        this.exInt2 = exInt2;
    }

    public Object getExVar1() {
        return exVar1;
    }

    public void setExVar1(Object exVar1) {
        this.exVar1 = exVar1;
    }

    public Object getExVar2() {
        return exVar2;
    }

    public void setExVar2(Object exVar2) {
        this.exVar2 = exVar2;
    }

    @Override
    public String toString() {
        return "VideoList{" +
                "galleryDetailsId=" + galleryDetailsId +
                ", galleryCatId=" + galleryCatId +
                ", pageId=" + pageId +
                ", sectionId=" + sectionId +
                ", typeVideoImage='" + typeVideoImage + '\'' +
                ", title='" + title + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", fileSize=" + fileSize +
                ", sortNo=" + sortNo +
                ", addDate='" + addDate + '\'' +
                ", editDate=" + editDate +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1=" + exVar1 +
                ", exVar2=" + exVar2 +
                '}';
    }
}
