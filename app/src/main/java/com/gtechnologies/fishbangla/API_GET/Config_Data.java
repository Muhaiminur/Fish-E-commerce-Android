package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config_Data{

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}