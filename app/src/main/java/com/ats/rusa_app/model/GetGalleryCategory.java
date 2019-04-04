package com.ats.rusa_app.model;

import java.util.List;

public class GetGalleryCategory {

    private int catId;
    private int parentId;
    private String catName;
    private String slugName;
    private String catCode;
    private String catDesc;
    private String imageName;
    private String externalUrl;
    private String externalUrlTarget;
    private String catEditDate;
    private String catAddDate;
    private int catSortNo;
    private String catRemark;
    private int sectionId;
    private int addedByUserId;
    private int editByUserId;
    private int isActive;
    private int delStatus;
    private String sectionName;
    private String categoryName;
    private int exInt2;
    List<CategoryDescription> categoryDescriptionList;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public String getCatCode() {
        return catCode;
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getExternalUrlTarget() {
        return externalUrlTarget;
    }

    public void setExternalUrlTarget(String externalUrlTarget) {
        this.externalUrlTarget = externalUrlTarget;
    }

    public String getCatEditDate() {
        return catEditDate;
    }

    public void setCatEditDate(String catEditDate) {
        this.catEditDate = catEditDate;
    }

    public String getCatAddDate() {
        return catAddDate;
    }

    public void setCatAddDate(String catAddDate) {
        this.catAddDate = catAddDate;
    }

    public int getCatSortNo() {
        return catSortNo;
    }

    public void setCatSortNo(int catSortNo) {
        this.catSortNo = catSortNo;
    }

    public String getCatRemark() {
        return catRemark;
    }

    public void setCatRemark(String catRemark) {
        this.catRemark = catRemark;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getExInt2() {
        return exInt2;
    }

    public void setExInt2(int exInt2) {
        this.exInt2 = exInt2;
    }

    public List<CategoryDescription> getCategoryDescriptionList() {
        return categoryDescriptionList;
    }

    public void setCategoryDescriptionList(List<CategoryDescription> categoryDescriptionList) {
        this.categoryDescriptionList = categoryDescriptionList;
    }

    @Override
    public String toString() {
        return "GetGalleryCategory{" +
                "catId=" + catId +
                ", parentId=" + parentId +
                ", catName='" + catName + '\'' +
                ", slugName='" + slugName + '\'' +
                ", catCode='" + catCode + '\'' +
                ", catDesc='" + catDesc + '\'' +
                ", imageName='" + imageName + '\'' +
                ", externalUrl='" + externalUrl + '\'' +
                ", externalUrlTarget='" + externalUrlTarget + '\'' +
                ", catEditDate='" + catEditDate + '\'' +
                ", catAddDate='" + catAddDate + '\'' +
                ", catSortNo=" + catSortNo +
                ", catRemark='" + catRemark + '\'' +
                ", sectionId=" + sectionId +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", sectionName='" + sectionName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", exInt2=" + exInt2 +
                ", categoryDescriptionList=" + categoryDescriptionList +
                '}';
    }
}
