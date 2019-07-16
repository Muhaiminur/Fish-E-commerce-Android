package com.gtechnologies.fishbangla.Model;

public class Fish_List {

    String image_url;

    String fish_price;

    String fish_location;

    String fish_category;

    public Fish_List() {
    }

    public Fish_List(String image_url, String fish_price, String fish_location, String fish_category) {
        this.image_url = image_url;
        this.fish_price = fish_price;
        this.fish_location = fish_location;
        this.fish_category = fish_category;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String value) {
        this.image_url = value;
    }

    public String getFish_price() {
        return this.fish_price;
    }

    public void setFish_price(String value) {
        this.fish_price = value;
    }

    public String getFish_location() {
        return this.fish_location;
    }

    public void setFish_location(String value) {
        this.fish_location = value;
    }

    public String getFish_category() {
        return this.fish_category;
    }

    public void setFish_category(String value) {
        this.fish_category = value;
    }

    @Override
    public String toString() {
        return "Fish_List{" +
                "image_url='" + image_url + '\'' +
                ", fish_price='" + fish_price + '\'' +
                ", fish_location='" + fish_location + '\'' +
                ", fish_category='" + fish_category + '\'' +
                '}';
    }
}