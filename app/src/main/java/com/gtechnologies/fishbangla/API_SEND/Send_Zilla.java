package com.gtechnologies.fishbangla.API_SEND;

public class Send_Zilla {
    String division_id;

    public Send_Zilla(String division_id) {
        this.division_id = division_id;
    }

    public Send_Zilla() {
    }

    public String getDivision_id() {
        return this.division_id;
    }

    public void setDivision_id(String value) {
        this.division_id = value;
    }

    @Override
    public String toString() {
        return "Send_Zilla{" +
                "division_id='" + division_id + '\'' +
                '}';
    }
}
