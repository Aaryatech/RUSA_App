package com.ats.rusa_app.model;

public class Sectionlist {

    private Integer sectionId;
    private String sectionName;
    private String sectionSlugname;
    private String sectionDesc;
    private Integer pageId;
    private Integer secSortNo;
    private Integer catCount;
    private Object externalUrl;
    private Object externalUrlTarget;
    private Object catList;

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionSlugname() {
        return sectionSlugname;
    }

    public void setSectionSlugname(String sectionSlugname) {
        this.sectionSlugname = sectionSlugname;
    }

    public String getSectionDesc() {
        return sectionDesc;
    }

    public void setSectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getSecSortNo() {
        return secSortNo;
    }

    public void setSecSortNo(Integer secSortNo) {
        this.secSortNo = secSortNo;
    }

    public Integer getCatCount() {
        return catCount;
    }

    public void setCatCount(Integer catCount) {
        this.catCount = catCount;
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

    public Object getCatList() {
        return catList;
    }

    public void setCatList(Object catList) {
        this.catList = catList;
    }

    @Override
    public String toString() {
        return "Sectionlist{" +
                "sectionId=" + sectionId +
                ", sectionName='" + sectionName + '\'' +
                ", sectionSlugname='" + sectionSlugname + '\'' +
                ", sectionDesc='" + sectionDesc + '\'' +
                ", pageId=" + pageId +
                ", secSortNo=" + secSortNo +
                ", catCount=" + catCount +
                ", externalUrl=" + externalUrl +
                ", externalUrlTarget=" + externalUrlTarget +
                ", catList=" + catList +
                '}';
    }
}
