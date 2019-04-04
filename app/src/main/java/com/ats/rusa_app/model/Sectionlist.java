package com.ats.rusa_app.model;

import java.util.List;

public class Sectionlist {

    private Integer sectionId;
    private String sectionName;
    private String sectionSlugname;
    private String sectionDesc;
    private Integer pageId;
    private Integer secSortNo;
    private Integer catCount;
    private String externalUrl;
    private String externalUrlTarget;
    private List<CategoryList> catList;

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

    public List<CategoryList> getCatList() {
        return catList;
    }

    public void setCatList(List<CategoryList> catList) {
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
                ", externalUrl='" + externalUrl + '\'' +
                ", externalUrlTarget='" + externalUrlTarget + '\'' +
                ", catList=" + catList +
                '}';
    }
}
