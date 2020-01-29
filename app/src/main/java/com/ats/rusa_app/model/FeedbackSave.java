package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackSave {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("retmsg")
    @Expose
    private String retmsg;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "FeedbackSave{" +
                "msg='" + msg + '\'' +
                ", retmsg='" + retmsg + '\'' +
                ", error=" + error +
                '}';
    }
}
