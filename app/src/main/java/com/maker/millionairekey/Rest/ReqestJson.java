package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReqestJson {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("oldpassword")
    @Expose
    private String oldpassword;



    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    @SerializedName("amount")
    @Expose
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /// deshborad

    @SerializedName("current_level")
    @Expose
    private String currentLevel;
    @SerializedName("totalreffral")
    @Expose
    private Integer totalreffral;
    @SerializedName("balance")
    @Expose
    private Object balance;
    @SerializedName("incomestatus")
    @Expose
    private String incomestatus;
    @SerializedName("leveldata")
    @Expose
    private List<Object> leveldata = null;

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Integer getTotalreffral() {
        return totalreffral;
    }

    public void setTotalreffral(Integer totalreffral) {
        this.totalreffral = totalreffral;
    }

    public Object getBalance() {
        return balance;
    }

    public void setBalance(Object balance) {
        this.balance = balance;
    }

    public String getIncomestatus() {
        return incomestatus;
    }

    public void setIncomestatus(String incomestatus) {
        this.incomestatus = incomestatus;
    }

    public List<Object> getLeveldata() {
        return leveldata;
    }

    public void setLeveldata(List<Object> leveldata) {
        this.leveldata = leveldata;
    }

}
