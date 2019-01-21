package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Achievementdatum {
    @SerializedName("noOfStar")
    @Expose
    private Integer noOfStar;
    @SerializedName("noOfUser")
    @Expose
    private Integer noOfUser;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;

    public Integer getNoOfStar() {
        return noOfStar;
    }


    public void setNoOfStar(Integer noOfStar) {
        this.noOfStar = noOfStar;
    }
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public Integer getNoOfUser() {
        return noOfUser;
    }

    public void setNoOfUser(Integer noOfUser) {
        this.noOfUser = noOfUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

}
