package com.maker.millionairekey.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserGetInfoResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("classdata")
    @Expose
    private UserInfo classdata;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public UserInfo getClassdata() {
        return classdata;
    }

    public void setClassdata(UserInfo classdata) {
        this.classdata = classdata;
    }
}
