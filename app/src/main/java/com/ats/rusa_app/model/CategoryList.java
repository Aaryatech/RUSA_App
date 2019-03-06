package com.ats.rusa_app.model;

public class CategoryList {

    private Integer catId;
    private String catName;
    private String catDesc;
    private String slugName;
    private Integer pageId;
    private Integer parentId;
    private Integer sectionId;
    private Integer subCatCount;
    private Integer catSortNo;
    private Object externalUrl;
    private Object externalUrlTarget;
    private Object subCatList;


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

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
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

    public Integer getSubCatCount() {
        return subCatCount;
    }

    public void setSubCatCount(Integer subCatCount) {
        this.subCatCount = subCatCount;
    }

    public Integer getCatSortNo() {
        return catSortNo;
    }

    public void setCatSortNo(Integer catSortNo) {
        this.catSortNo = catSortNo;
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

    public Object getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(Object subCatList) {
        this.subCatList = subCatList;
    }

    @Override
    public String toString() {
        return "CategoryList{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                ", catDesc='" + catDesc + '\'' +
                ", slugName='" + slugName + '\'' +
                ", pageId=" + pageId +
                ", parentId=" + parentId +
                ", sectionId=" + sectionId +
                ", subCatCount=" + subCatCount +
                ", catSortNo=" + catSortNo +
                ", externalUrl=" + externalUrl +
                ", externalUrlTarget=" + externalUrlTarget +
                ", subCatList=" + subCatList +
                '}';
    }
}
