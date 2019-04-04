package com.ats.rusa_app.model;

import java.util.List;

public class PageData {

    private Integer pageId;
    private String pageName;
    private String slugName;
    private Integer sectioinId;
    private List<CmsContentList> cmsContentList;
    private List<FaqContentList> faqContentList;
    private List<DocumentUploadList> documentUploadList;
    private List<TestImonialList> testImonialList;
    private List<GallaryDetailList> gallaryDetailList;
    private List<DetailNewsList> detailNewsList;
    private List<TeamList> teamList;
    private List<SuccessList> successList;
    private List<ImageListByCategory> imageListByCategory;
    private List<VideoList> videoList;

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }

    public Integer getSectioinId() {
        return sectioinId;
    }

    public void setSectioinId(Integer sectioinId) {
        this.sectioinId = sectioinId;
    }

    public List<CmsContentList> getCmsContentList() {
        return cmsContentList;
    }

    public void setCmsContentList(List<CmsContentList> cmsContentList) {
        this.cmsContentList = cmsContentList;
    }

    public List<FaqContentList> getFaqContentList() {
        return faqContentList;
    }

    public void setFaqContentList(List<FaqContentList> faqContentList) {
        this.faqContentList = faqContentList;
    }

    public List<DocumentUploadList> getDocumentUploadList() {
        return documentUploadList;
    }

    public void setDocumentUploadList(List<DocumentUploadList> documentUploadList) {
        this.documentUploadList = documentUploadList;
    }

    public List<TestImonialList> getTestImonialList() {
        return testImonialList;
    }

    public void setTestImonialList(List<TestImonialList> testImonialList) {
        this.testImonialList = testImonialList;
    }

    public List<GallaryDetailList> getGallaryDetailList() {
        return gallaryDetailList;
    }

    public void setGallaryDetailList(List<GallaryDetailList> gallaryDetailList) {
        this.gallaryDetailList = gallaryDetailList;
    }

    public List<DetailNewsList> getDetailNewsList() {
        return detailNewsList;
    }

    public void setDetailNewsList(List<DetailNewsList> detailNewsList) {
        this.detailNewsList = detailNewsList;
    }

    public List<TeamList> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamList> teamList) {
        this.teamList = teamList;
    }

    public List<SuccessList> getSuccessList() {
        return successList;
    }

    public void setSuccessList(List<SuccessList> successList) {
        this.successList = successList;
    }

    public List<ImageListByCategory> getImageListByCategory() {
        return imageListByCategory;
    }

    public void setImageListByCategory(List<ImageListByCategory> imageListByCategory) {
        this.imageListByCategory = imageListByCategory;
    }

    public List<VideoList> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoList> videoList) {
        this.videoList = videoList;
    }


    @Override
    public String toString() {
        return "PageData{" +
                "pageId=" + pageId +
                ", pageName='" + pageName + '\'' +
                ", slugName='" + slugName + '\'' +
                ", sectioinId=" + sectioinId +
                ", cmsContentList=" + cmsContentList +
                ", faqContentList=" + faqContentList +
                ", documentUploadList=" + documentUploadList +
                ", testImonialList=" + testImonialList +
                ", gallaryDetailList=" + gallaryDetailList +
                ", detailNewsList=" + detailNewsList +
                ", teamList=" + teamList +
                ", successList=" + successList +
                ", imageListByCategory=" + imageListByCategory +
                ", videoList=" + videoList +
                '}';
    }
}
