package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_ADDRESS {
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("receiversName")
    @Expose
    private String receiversName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userContact")
    @Expose
    private String userContact;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("nearbyLandmark")
    @Expose
    private String nearbyLandmark;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("receiversContact")
    @Expose
    private String receiversContact;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("upazilla")
    @Expose
    private String upazilla;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getReceiversName() {
        return receiversName;
    }

    public void setReceiversName(String receiversName) {
        this.receiversName = receiversName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNearbyLandmark() {
        return nearbyLandmark;
    }

    public void setNearbyLandmark(String nearbyLandmark) {
        this.nearbyLandmark = nearbyLandmark;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getReceiversContact() {
        return receiversContact;
    }

    public void setReceiversContact(String receiversContact) {
        this.receiversContact = receiversContact;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GET_ADDRESS{" +
                "profession='" + profession + '\'' +
                ", receiversName='" + receiversName + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", userName='" + userName + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userId=" + userId +
                ", nearbyLandmark='" + nearbyLandmark + '\'' +
                ", division='" + division + '\'' +
                ", receiversContact='" + receiversContact + '\'' +
                ", district='" + district + '\'' +
                ", id=" + id +
                ", village='" + village + '\'' +
                ", upazilla='" + upazilla + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
