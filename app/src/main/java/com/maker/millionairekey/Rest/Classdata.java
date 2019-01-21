package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Classdata {
    @SerializedName("current_level")
    @Expose
    private String currentLevel;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("totalreffral")
    @Expose
    private Integer totalreffral;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("incomestatus")
    @Expose
    private String incomestatus;
    @SerializedName("leveldata")
    @Expose
    private List<Leveldatum> leveldata = null;
    @SerializedName("isBankInfo")
    @Expose
    private Boolean isBankInfo;
    @SerializedName("achievementLevel")
    @Expose
    private String achievementLevel;

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }
    public String getAchievementLevel() {
        return achievementLevel;
    }

    public void setAchievementLevel(String achievementLevel) {
        this.achievementLevel = achievementLevel;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTotalreffral() {
        return totalreffral;
    }

    public void setTotalreffral(Integer totalreffral) {
        this.totalreffral = totalreffral;
    }

    public Double getBalance() {
        return balance;
    }
    public Boolean getIsBankInfo() {
        return isBankInfo;
    }

    public void setIsBankInfo(Boolean isBankInfo) {
        this.isBankInfo = isBankInfo;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getIncomestatus() {
        return incomestatus;
    }

    public void setIncomestatus(String incomestatus) {
        this.incomestatus = incomestatus;
    }

    public List<Leveldatum> getLeveldata() {
        return leveldata;
    }

    public void setLeveldata(List<Leveldatum> leveldata) {
        this.leveldata = leveldata;
    }



    @SerializedName("totalpaid")
    @Expose
    private String totalpaid;
    @SerializedName("Earning")
    @Expose
    private String earning;

    public String getTotalpaid() {
        return totalpaid;
    }

    public void setTotalpaid(String totalpaid) {
        this.totalpaid = totalpaid;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

}
