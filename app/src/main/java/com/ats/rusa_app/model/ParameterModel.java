package com.ats.rusa_app.model;

public class ParameterModel {

    private int regId;

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    @Override
    public String toString() {
        return "ParameterModel{" +
                "regId=" + regId +
                '}';
    }
}
