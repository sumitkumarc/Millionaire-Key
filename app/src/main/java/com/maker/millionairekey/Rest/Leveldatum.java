package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leveldatum {
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("levelno")
    @Expose

    private String levelno;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("activeStatus")
    @Expose
    private String activeStatus;


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLevelno() {
        return levelno;
    }

    public void setLevelno(String levelno) {
        this.levelno = levelno;
    }

    public String getUsername() {
        return username;
    }
    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
