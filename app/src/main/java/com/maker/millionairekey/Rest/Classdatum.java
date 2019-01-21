package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Classdatum {
    @SerializedName("transactionno")
    @Expose
    private String transactionno;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ondate")
    @Expose
    private String ondate;

    public String getTransactionno() {
        return transactionno;
    }

    public void setTransactionno(String transactionno) {
        this.transactionno = transactionno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOndate() {
        return ondate;
    }
    public void setOndate(String ondate) {
        this.ondate = ondate;
    }

    // userinfo

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
    @SerializedName("wallet")
    @Expose
    private Integer wallet;
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

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

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

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    @SerializedName("news")
    @Expose
    private String news;



    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }


    @SerializedName("noOfStar")
    @Expose
    private Integer noOfStar;
    @SerializedName("noOfUser")
    @Expose
    private Integer noOfUser;

    @SerializedName("activeStatus")
    @Expose
    private Boolean activeStatus;

    public Integer getNoOfStar() {
        return noOfStar;
    }

    public void setNoOfStar(Integer noOfStar) {
        this.noOfStar = noOfStar;
    }

    public Integer getNoOfUser() {
        return noOfUser;
    }

    public void setNoOfUser(Integer noOfUser) {
        this.noOfUser = noOfUser;
    }



    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

}
