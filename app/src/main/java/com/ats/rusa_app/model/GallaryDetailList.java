package com.ats.rusa_app.model;

public class GallaryDetailList {

    private int galleryDetailsId;
    private int galleryCatId;
    private int pageId;
    private int sectionId;
    private String typeVideoImage;
    private String title;
    private String fileName;
    private String fileType;
    private String fileSize;
    private int sortNo;
    private String addDate;
    private String editDate;
    private int addedByUserId;
    private int editByUserId;
    private int isActive;
    private int delStatus;
    private int exInt1;
    private int exInt2;
    private String exVar1;
    private String exVar2;

    public GallaryDetailList(int galleryDetailsId, int galleryCatId, int pageId, int sectionId, String typeVideoImage, String title, String fileName, String fileType, String fileSize, int sortNo, String addDate, String editDate, int addedByUserId, int editByUserId, int isActive, int delStatus, int exInt1, int exInt2, String exVar1, String exVar2) {
        this.galleryDetailsId = galleryDetailsId;
        this.galleryCatId = galleryCatId;
        this.pageId = pageId;
        this.sectionId = sectionId;
        this.typeVideoImage = typeVideoImage;
        this.title = title;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.sortNo = sortNo;
        this.addDate = addDate;
        this.editDate = editDate;
        this.addedByUserId = addedByUserId;
        this.editByUserId = editByUserId;
        this.isActive = isActive;
        this.delStatus = delStatus;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
    }

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

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
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

    public int getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(int addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public int getEditByUserId() {
        return editByUserId;
    }

    public void setEditByUserId(int editByUserId) {
        this.editByUserId = editByUserId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getExInt1() {
        return exInt1;
    }

    public void setExInt1(int exInt1) {
        this.exInt1 = exInt1;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    @Override
    public String toString() {
        return "GallaryDetailList{" +
                "galleryDetailsId=" + galleryDetailsId +
                ", galleryCatId=" + galleryCatId +
                ", pageId=" + pageId +
                ", sectionId=" + sectionId +
                ", typeVideoImage='" + typeVideoImage + '\'' +
                ", title='" + title + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", sortNo=" + sortNo +
                ", addDate='" + addDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                '}';
    }
}
