package com.gtechnologies.fishbangla.Model;

public class Cart_List {
    String d_id;
    String image_url;

    String fish_name;

    String fish_price;

    String fish_quantity;

    public Cart_List(String d_id, String image_url, String fish_name, String fish_price, String fish_quantity) {
        this.d_id = d_id;
        this.image_url = image_url;
        this.fish_name = fish_name;
        this.fish_price = fish_price;
        this.fish_quantity = fish_quantity;
    }

    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }

    public Cart_List() {
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String value) {
        this.image_url = value;
    }

    public String getFish_name() {
        return this.fish_name;
    }

    public void setFish_name(String value) {
        this.fish_name = value;
    }

    public String getFish_price() {
        return this.fish_price;
    }

    public void setFish_price(String value) {
        this.fish_price = value;
    }

    public String getFish_quantity() {
        return this.fish_quantity;
    }

    public void setFish_quantity(String value) {
        this.fish_quantity = value;
    }

    @Override
    public String toString() {
        return "Cart_List{" +
                "d_id='" + d_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", fish_name='" + fish_name + '\'' +
                ", fish_price='" + fish_price + '\'' +
                ", fish_quantity='" + fish_quantity + '\'' +
                '}';
    }
}
