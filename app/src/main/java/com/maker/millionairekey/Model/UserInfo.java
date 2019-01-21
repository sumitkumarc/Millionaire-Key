package com.maker.millionairekey.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("refcode")
    @Expose
    private String refcode;
    @SerializedName("Gender")
    @Expose
    private Object gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private Object address;
//    @SerializedName("wallet")
//    @Expose
//    private Integer wallet;
    @SerializedName("panno")
    @Expose
    private String panno;
    @SerializedName("bankname")
    @Expose
    private String bankname;
    @SerializedName("branchname")
    @Expose
    private String branchname;
    @SerializedName("accno")
    @Expose
    private String accno;
    @SerializedName("ifsccode")
    @Expose
    private String ifsccode;
    @SerializedName("adharcard")
    @Expose
    private String adharcard;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

//    public Integer getWallet() {
//        return wallet;
//    }
//
//    public void setWallet(Integer wallet) {
//        this.wallet = wallet;
//    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getAdharcard() {
        return adharcard;
    }

    public void setAdharcard(String adharcard) {
        this.adharcard = adharcard;
    }

}
