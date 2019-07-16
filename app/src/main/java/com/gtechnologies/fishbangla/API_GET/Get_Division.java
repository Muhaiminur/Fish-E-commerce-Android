package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_Division {
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("divisionBn")
    @Expose
    private String divisionBn;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivisionBn() {
        return divisionBn;
    }

    public void setDivisionBn(String divisionBn) {
        this.divisionBn = divisionBn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
