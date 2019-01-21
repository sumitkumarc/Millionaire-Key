package com.maker.millionairekey.Rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {


    //  registration info

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sid")
    @Expose
    private Integer sid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
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
    @SerializedName("isactive")
    @Expose
    private Boolean isactive;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("ispaid")
    @Expose
    private Boolean ispaid;
    @SerializedName("ispayeable")
    @Expose
    private Boolean ispayeable;
    @SerializedName("wallet")
    @Expose
    private Double wallet;
    @SerializedName("txid")
    @Expose
    private String txid;
    @SerializedName("txrespo")
    @Expose
    private String txrespo;
    @SerializedName("paystatus")
    @Expose
    private String paystatus;
    @SerializedName("q1")
    @Expose
    private Integer q1;
    @SerializedName("ans1")
    @Expose
    private String ans1;
    @SerializedName("q2")
    @Expose
    private Integer q2;
    @SerializedName("ans2")
    @Expose
    private String ans2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

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

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIspaid() {
        return ispaid;
    }

    public void setIspaid(Boolean ispaid) {
        this.ispaid = ispaid;
    }

    public Boolean getIspayeable() {
        return ispayeable;
    }

    public void setIspayeable(Boolean ispayeable) {
        this.ispayeable = ispayeable;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getTxrespo() {
        return txrespo;
    }

    public void setTxrespo(String txrespo) {
        this.txrespo = txrespo;
    }

    public String getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(String paystatus) {
        this.paystatus = paystatus;
    }

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }


    // bank info

    @SerializedName("bankname")
    @Expose
    private String bankname;
    @SerializedName("branchname")
    @Expose
    private String branchname;
    @SerializedName("acno")
    @Expose
    private String acno;
    @SerializedName("ifsccode")
    @Expose
    private String ifsccode;
    @SerializedName("aadharcard")
    @Expose
    private String aadharcard;
    @SerializedName("panno")
    @Expose
    private String panno;

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

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getAadharcard() {
        return aadharcard;
    }

    public void setAadharcard(String aadharcard) {
        this.aadharcard = aadharcard;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_description")
    @Expose
    private String errorDescription;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    // DashBord

    @SerializedName("classdata")
    @Expose
    private Classdata classdata;


    public Classdata getClassdata() {
        return classdata;
    }

    public void setClassdata(Classdata classdata) {
        this.classdata = classdata;
    }



    @SerializedName("payrespo")
    @Expose
    private String payrespo;

    public String getPayrespo() {
        return payrespo;
    }

    public void setPayrespo(String payrespo) {
        this.payrespo = payrespo;
    }




}
