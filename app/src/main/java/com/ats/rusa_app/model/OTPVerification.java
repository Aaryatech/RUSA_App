package com.ats.rusa_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPVerification {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("reg")
    @Expose
    private Reg reg;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Reg getReg() {
        return reg;
    }

    public void setReg(Reg reg) {
        this.reg = reg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "OTPVerification{" +
                "msg='" + msg + '\'' +
                ", reg=" + reg +
                ", error=" + error +
                '}';
    }
}
