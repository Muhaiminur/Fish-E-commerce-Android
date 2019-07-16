package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_Zilla {
    @SerializedName("districtBn")
    @Expose
    private String districtBn;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("divisionName")
    @Expose
    private String divisionName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;

    public String getDistrictBn() {
        return districtBn;
    }

    public void setDistrictBn(String districtBn) {
        this.districtBn = districtBn;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }
}
