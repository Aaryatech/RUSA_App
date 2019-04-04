package com.ats.rusa_app.model;

public class CategoryDescription {

    private int catDescId;
    private int catId;
    private String catName;
    private String catDesc;
    private int languageId;
    private String exVar1;

    public int getCatDescId() {
        return catDescId;
    }

    public void setCatDescId(int catDescId) {
        this.catDescId = catDescId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
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

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    @Override
    public String toString() {
        return "CategoryDescription{" +
                "catDescId=" + catDescId +
                ", catId=" + catId +
                ", catName='" + catName + '\'' +
                ", catDesc='" + catDesc + '\'' +
                ", languageId=" + languageId +
                ", exVar1='" + exVar1 + '\'' +
                '}';
    }
}
