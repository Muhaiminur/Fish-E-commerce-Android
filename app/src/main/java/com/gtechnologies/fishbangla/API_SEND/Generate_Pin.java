package com.gtechnologies.fishbangla.API_SEND;

public class Generate_Pin {

    String userMobile;

    public Generate_Pin(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMobile() {
        return this.userMobile;
    }

    public void setUserMobile(String value) {
        this.userMobile = value;
    }

    @Override
    public String toString() {
        return "Generate_Pin{" +
                "userMobile='" + userMobile + '\'' +
                '}';
    }
}