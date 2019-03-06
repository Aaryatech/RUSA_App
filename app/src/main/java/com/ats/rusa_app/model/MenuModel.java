package com.ats.rusa_app.model;

import java.util.List;

public class MenuModel {

    private List<Sectionlist> sectionlist = null;
    private List<CategoryList> categoryList = null;
    private List<SubCatList> subCatList = null;

    public List<Sectionlist> getSectionlist() {
        return sectionlist;
    }

    public void setSectionlist(List<Sectionlist> sectionlist) {
        this.sectionlist = sectionlist;
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public List<SubCatList> getSubCatList() {
        return subCatList;
    }

    public void setSubCatList(List<SubCatList> subCatList) {
        this.subCatList = subCatList;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "sectionlist=" + sectionlist +
                ", categoryList=" + categoryList +
                ", subCatList=" + subCatList +
                '}';
    }
}
