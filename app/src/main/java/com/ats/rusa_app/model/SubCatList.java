package com.ats.rusa_app.model;

public class SubCatList {

    private Integer subCatId;
    private String subCatName;
    private String subCatDesc;
    private String subSlugName;
    private Integer pageId;
    private Integer parentId;
    private Integer sectionId;
    private Integer subSortNo;
    private Object externalUrl;
    private Object externalUrlTarget;

    public Integer getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Integer subCatId) {
        this.subCatId = subCatId;
    }

    public String getSubCatName() {
        return subCatName;
    }

    public void setSubCatName(String subCatName) {
        this.subCatName = subCatName;
    }

    public String getSubCatDesc() {
        return subCatDesc;
    }

    public void setSubCatDesc(String subCatDesc) {
        this.subCatDesc = subCatDesc;
    }

    public String getSubSlugName() {
        return subSlugName;
    }

    public void setSubSlugName(String subSlugName) {
        this.subSlugName = subSlugName;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getSubSortNo() {
        return subSortNo;
    }

    public void setSubSortNo(Integer subSortNo) {
        this.subSortNo = subSortNo;
    }

    public Object getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(Object externalUrl) {
        this.externalUrl = externalUrl;
    }

    public Object getExternalUrlTarget() {
        return externalUrlTarget;
    }

    public void setExternalUrlTarget(Object externalUrlTarget) {
        this.externalUrlTarget = externalUrlTarget;
    }

    @Override
    public String toString() {
        return "SubCatList{" +
                "subCatId=" + subCatId +
                ", subCatName='" + subCatName + '\'' +
                ", subCatDesc='" + subCatDesc + '\'' +
                ", subSlugName='" + subSlugName + '\'' +
                ", pageId=" + pageId +
                ", parentId=" + parentId +
                ", sectionId=" + sectionId +
                ", subSortNo=" + subSortNo +
                ", externalUrl=" + externalUrl +
                ", externalUrlTarget=" + externalUrlTarget +
                '}';
    }
}
