package com.gtechnologies.fishbangla.API_SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Send_Registration {

    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("referCode")
    @Expose
    private String referCode;
    @SerializedName("bReferCode")
    @Expose
    private String bReferCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    public Send_Registration() {
    }
    public Send_Registration(String contact, String tag, String referCode, String bReferCode, String name, String email) {
        super();
        this.contact = contact;
        this.tag = tag;
        this.referCode = referCode;
        this.bReferCode = bReferCode;
        this.name = name;
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getBReferCode() {
        return bReferCode;
    }

    public void setBReferCode(String bReferCode) {
        this.bReferCode = bReferCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}