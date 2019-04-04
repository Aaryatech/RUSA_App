package com.ats.rusa_app.model;

public class MenuGroup {

    public String menuName, url;
    public boolean hasChildren, isGroup,isExtUrl;



    public MenuGroup(String menuName, boolean isGroup, boolean hasChildren,boolean isExtUrl, String url) {

        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.isExtUrl = isExtUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public boolean isExtUrl() {
        return isExtUrl;
    }

    public void setExtUrl(boolean extUrl) {
        isExtUrl = extUrl;
    }

    @Override
    public String toString() {
        return "MenuGroup{" +
                "menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", hasChildren=" + hasChildren +
                ", isGroup=" + isGroup +
                ", isExtUrl=" + isExtUrl +
                '}';
    }
}
