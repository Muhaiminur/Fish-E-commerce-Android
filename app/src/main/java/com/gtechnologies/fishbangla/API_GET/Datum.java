package com.gtechnologies.fishbangla.API_GET;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameBn")
    @Expose
    private String nameBn;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private String status;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameBn() {
        return nameBn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}