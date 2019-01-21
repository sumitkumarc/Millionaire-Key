package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {
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
    private List<Classdatum> classdata = null;

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

    public List<Classdatum> getClassdata() {
        return classdata;
    }

    public void setClassdata(List<Classdatum> classdata) {
        this.classdata = classdata;
    }

}
