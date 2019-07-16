package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Get_Fish_List implements Serializable {
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("saleType")
    @Expose
    private String saleType;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("nameBn")
    @Expose
    private String nameBn;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("frozen")
    @Expose
    private String frozen;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("readyFood")
    @Expose
    private String readyFood;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("upazilla")
    @Expose
    private String upazilla;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getNameBn() {
        return nameBn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getReadyFood() {
        return readyFood;
    }

    public void setReadyFood(String readyFood) {
        this.readyFood = readyFood;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }

    @Override
    public String toString() {
        return "Get_Fish_List{" +
                "image='" + image + '\'' +
                ", quantity='" + quantity + '\'' +
                ", userName='" + userName + '\'' +
                ", saleType='" + saleType + '\'' +
                ", available='" + available + '\'' +
                ", nameBn='" + nameBn + '\'' +
                ", description='" + description + '\'' +
                ", frozen='" + frozen + '\'' +
                ", video='" + video + '\'' +
                ", division='" + division + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", userId=" + userId +
                ", price='" + price + '\'' +
                ", district='" + district + '\'' +
                ", readyFood='" + readyFood + '\'' +
                ", id=" + id +
                ", tag='" + tag + '\'' +
                ", upazilla='" + upazilla + '\'' +
                '}';
    }
}
