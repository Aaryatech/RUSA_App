package com.ats.rusa_app.model;

public class CmsContentList {

    private Integer cmsPageId;
    private Integer pageId;
    private Integer parentMenu;
    private Integer pageOrder;
    private Object featuredImage;
    private String featuredImageAlignment;
    private Object downloadPdf;
    private Integer isActive;
    private Integer delStatus;
    private String addDate;
    private String editDate;
    private Integer addedByUserId;
    private Integer editByUserId;
    private String heading;
    private String smallheading;
    private String pageDesc;
    private Integer languageId;

    public Integer getCmsPageId() {
        return cmsPageId;
    }

    public void setCmsPageId(Integer cmsPageId) {
        this.cmsPageId = cmsPageId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Integer parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Integer getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(Integer pageOrder) {
        this.pageOrder = pageOrder;
    }

    public Object getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(Object featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageAlignment() {
        return featuredImageAlignment;
    }

    public void setFeaturedImageAlignment(String featuredImageAlignment) {
        this.featuredImageAlignment = featuredImageAlignment;
    }

    public Object getDownloadPdf() {
        return downloadPdf;
    }

    public void setDownloadPdf(Object downloadPdf) {
        this.downloadPdf = downloadPdf;
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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSmallheading() {
        return smallheading;
    }

    public void setSmallheading(String smallheading) {
        this.smallheading = smallheading;
    }

    public String getPageDesc() {
        return pageDesc;
    }

    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "CmsContentList{" +
                "cmsPageId=" + cmsPageId +
                ", pageId=" + pageId +
                ", parentMenu=" + parentMenu +
                ", pageOrder=" + pageOrder +
                ", featuredImage=" + featuredImage +
                ", featuredImageAlignment='" + featuredImageAlignment + '\'' +
                ", downloadPdf=" + downloadPdf +
                ", isActive=" + isActive +
                ", delStatus=" + delStatus +
                ", addDate='" + addDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", addedByUserId=" + addedByUserId +
                ", editByUserId=" + editByUserId +
                ", heading='" + heading + '\'' +
                ", smallheading='" + smallheading + '\'' +
                ", pageDesc='" + pageDesc + '\'' +
                ", languageId=" + languageId +
                '}';
    }
}
