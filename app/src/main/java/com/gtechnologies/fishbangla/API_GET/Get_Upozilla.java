package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_Upozilla {
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("upazillaBn")
    @Expose
    private String upazillaBn;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("upazilla")
    @Expose
    private String upazilla;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getUpazillaBn() {
        return upazillaBn;
    }

    public void setUpazillaBn(String upazillaBn) {
        this.upazillaBn = upazillaBn;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }
}
