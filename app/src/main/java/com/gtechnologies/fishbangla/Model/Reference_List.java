package com.gtechnologies.fishbangla.Model;

public class Reference_List {

    String image_url;

    String name;

    String time;

    public Reference_List(String image_url, String name, String time) {
        this.image_url = image_url;
        this.name = name;
        this.time = time;
    }

    public Reference_List() {
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String value) {
        this.image_url = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String value) {
        this.time = value;
    }
}