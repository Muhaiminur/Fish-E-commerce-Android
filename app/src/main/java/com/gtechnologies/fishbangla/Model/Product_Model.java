package com.gtechnologies.fishbangla.Model;

public class Product_Model {
    String name;
    String category;
    String upozilla;
    String sell;
    String froze;

    public Product_Model(String name, String category, String upozilla, String sell, String froze) {
        this.name = name;
        this.category = category;
        this.upozilla = upozilla;
        this.sell = sell;
        this.froze = froze;
    }

    public Product_Model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpozilla() {
        return upozilla;
    }

    public void setUpozilla(String upozilla) {
        this.upozilla = upozilla;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getFroze() {
        return froze;
    }

    public void setFroze(String froze) {
        this.froze = froze;
    }

    @Override
    public String toString() {
        return "Product_Model{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", upozilla='" + upozilla + '\'' +
                ", sell='" + sell + '\'' +
                ", froze='" + froze + '\'' +
                '}';
    }
}
