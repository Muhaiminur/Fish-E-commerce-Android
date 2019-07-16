package com.gtechnologies.fishbangla.Model;

public class Address_List {
    String name;

    String mobile;

    String address;

    String zilla;

    public Address_List() {
    }

    public Address_List(String name, String mobile, String address, String zilla) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.zilla = zilla;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getZilla() {
        return this.zilla;
    }

    public void setZilla(String value) {
        this.zilla = value;
    }
}