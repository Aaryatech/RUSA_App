package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreviousRecord {
    @SerializedName("prevId")
    @Expose
    private Integer prevId;
    @SerializedName("regId")
    @Expose
    private Integer regId;
    @SerializedName("record")
    @Expose
    private String record;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("extraVar1")
    @Expose
    private String extraVar1;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public PreviousRecord(Integer prevId, Integer regId, String record, String lastUpdate, String extraVar1) {
        this.prevId = prevId;
        this.regId = regId;
        this.record = record;
        this.lastUpdate = lastUpdate;
        this.extraVar1 = extraVar1;
    }

    public PreviousRecord(Integer prevId, Integer regId, String record, String lastUpdate, String extraVar1, String message, Boolean error) {
        this.prevId = prevId;
        this.regId = regId;
        this.record = record;
        this.lastUpdate = lastUpdate;
        this.extraVar1 = extraVar1;
        this.message = message;
        this.error = error;
    }

    public Integer getPrevId() {
        return prevId;
    }

    public void setPrevId(Integer prevId) {
        this.prevId = prevId;
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getExtraVar1() {
        return extraVar1;
    }

    public void setExtraVar1(String extraVar1) {
        this.extraVar1 = extraVar1;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PreviousRecord{" +
                "prevId=" + prevId +
                ", regId=" + regId +
                ", record=" + record +
                ", lastUpdate=" + lastUpdate +
                ", extraVar1=" + extraVar1 +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
