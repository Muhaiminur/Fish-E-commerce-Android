package com.gtechnologies.fishbangla.API_SEND;

public class Send_Upozilla {
    String district_id;

    public Send_Upozilla(String district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_id() {
        return this.district_id;
    }

    public void setDistrict_id(String value) {
        this.district_id = value;
    }

    @Override
    public String toString() {
        return "Send_Upozilla{" +
                "district_id='" + district_id + '\'' +
                '}';
    }
}
