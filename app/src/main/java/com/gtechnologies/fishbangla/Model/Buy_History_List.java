package com.gtechnologies.fishbangla.Model;

public class Buy_History_List {

    String name;

    String price;

    String unit;

    String time;

    public Buy_History_List() {
    }

    public Buy_History_List(String name, String price, String unit, String time) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String value) {
        this.price = value;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String value) {
        this.unit = value;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String value) {
        this.time = value;
    }
}