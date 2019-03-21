package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CmsList {

    @SerializedName("cmsPageId")
    @Expose
    private Integer cmsPageId;
    @SerializedName("moduleId")
    @Expose
    private Integer moduleId;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("pageDesc")
    @Expose
    private String pageDesc;
    @SerializedName("addDate")
    @Expose
    private String addDate;
    @SerializedName("editDate")
    @Expose
    private String editDate;
    @SerializedName("pageName")
    @Expose
    private String pageName;
    @SerializedName("pageSlug")
    @Expose
    private String pageSlug;

    public Integer getCmsPageId() {
        return cmsPageId;
    }

    public void setCmsPageId(Integer cmsPageId) {
        this.cmsPageId = cmsPageId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getPageDesc() {
        return pageDesc;
    }

    public void setPageDesc(String pageDesc) {
        this.pageDesc = pageDesc;
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

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageSlug() {
        return pageSlug;
    }

    public void setPageSlug(String pageSlug) {
        this.pageSlug = pageSlug;
    }

    @Override
    public String toString() {
        return "CmsList{" +
                "cmsPageId=" + cmsPageId +
                ", moduleId=" + moduleId +
                ", heading='" + heading + '\'' +
                ", pageDesc='" + pageDesc + '\'' +
                ", addDate='" + addDate + '\'' +
                ", editDate='" + editDate + '\'' +
                ", pageName='" + pageName + '\'' +
                ", pageSlug='" + pageSlug + '\'' +
                '}';
    }
}
