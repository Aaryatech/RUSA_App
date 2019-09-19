package com.ats.rusa_app.model;

public class DocUpload {

    private int docId;
    private int regId;
    private String title;
    private String fileName;
    private int typeId;
    private int delStatus;
    private int extraInt1;
    private String extraVarchar1;
    private String uploadDateTime;
    private long docSize;
    private String typeName;

    public DocUpload() {
    }

    public DocUpload(int docId, int regId, String title, String fileName, int typeId, int delStatus, String uploadDateTime, long docSize, String typeName) {
        this.docId = docId;
        this.regId = regId;
        this.title = title;
        this.fileName = fileName;
        this.typeId = typeId;
        this.delStatus = delStatus;
        this.uploadDateTime = uploadDateTime;
        this.docSize = docSize;
        this.typeName = typeName;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(int delStatus) {
        this.delStatus = delStatus;
    }

    public int getExtraInt1() {
        return extraInt1;
    }

    public void setExtraInt1(int extraInt1) {
        this.extraInt1 = extraInt1;
    }

    public String getExtraVarchar1() {
        return extraVarchar1;
    }

    public void setExtraVarchar1(String extraVarchar1) {
        this.extraVarchar1 = extraVarchar1;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public long getDocSize() {
        return docSize;
    }

    public void setDocSize(long docSize) {
        this.docSize = docSize;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "DocUpload{" +
                "docId=" + docId +
                ", regId=" + regId +
                ", title='" + title + '\'' +
                ", fileName='" + fileName + '\'' +
                ", typeId=" + typeId +
                ", delStatus=" + delStatus +
                ", extraInt1=" + extraInt1 +
                ", extraVarchar1='" + extraVarchar1 + '\'' +
                ", uploadDateTime='" + uploadDateTime + '\'' +
                ", docSize=" + docSize +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
