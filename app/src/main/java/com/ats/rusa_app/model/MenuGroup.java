package com.ats.rusa_app.model;

public class MenuGroup {

    public String menuName, url;
    public boolean hasChildren, isGroup;

    public MenuGroup(String menuName, boolean isGroup, boolean hasChildren, String url) {

        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
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

    @Override
    public String toString() {
        return "MenuGroup{" +
                "menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", hasChildren=" + hasChildren +
                ", isGroup=" + isGroup +
                '}';
    }
}
