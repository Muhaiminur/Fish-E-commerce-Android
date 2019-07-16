package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_LOGIN {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("referCode")
    @Expose
    private String referCode;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("buyerReferCode")
    @Expose
    private String buyerReferCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("referenceCode")
    @Expose
    private String referenceCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBuyerReferCode() {
        return buyerReferCode;
    }

    public void setBuyerReferCode(String buyerReferCode) {
        this.buyerReferCode = buyerReferCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "GET_LOGIN{" +
                "createdAt='" + createdAt + '\'' +
                ", referCode='" + referCode + '\'' +
                ", contact='" + contact + '\'' +
                ", buyerReferCode='" + buyerReferCode + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", referenceCode='" + referenceCode + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}