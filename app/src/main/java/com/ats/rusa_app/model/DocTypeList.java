package com.ats.rusa_app.model;

public class DocTypeList {

    private Integer typeId;
    private String typeName;
    private Integer delStatus;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "DocTypeList{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", delStatus=" + delStatus +
                '}';
    }
}
