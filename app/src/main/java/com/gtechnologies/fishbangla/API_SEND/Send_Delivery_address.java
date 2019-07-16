package com.gtechnologies.fishbangla.API_SEND;

public class Send_Delivery_address {
    String receiversName;

    String receiversContact;

    String address;

    String village;

    String nearbyLandmark;

    String profession;

    String latitude;

    String longitude;

    String status;

    String user;

    String division;

    String district;

    String upazilla;

    public Send_Delivery_address(String receiversName, String receiversContact, String address, String village, String nearbyLandmark, String profession, String latitude, String longitude, String status, String user, String division, String district, String upazilla) {
        this.receiversName = receiversName;
        this.receiversContact = receiversContact;
        this.address = address;
        this.village = village;
        this.nearbyLandmark = nearbyLandmark;
        this.profession = profession;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.user = user;
        this.division = division;
        this.district = district;
        this.upazilla = upazilla;
    }

    public String getReceiversName() {
        return this.receiversName;
    }

    public void setReceiversName(String value) {
        this.receiversName = value;
    }

    public String getReceiversContact() {
        return this.receiversContact;
    }

    public void setReceiversContact(String value) {
        this.receiversContact = value;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public String getVillage() {
        return this.village;
    }

    public void setVillage(String value) {
        this.village = value;
    }

    public String getNearbyLandmark() {
        return this.nearbyLandmark;
    }

    public void setNearbyLandmark(String value) {
        this.nearbyLandmark = value;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String value) {
        this.profession = value;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String value) {
        this.latitude = value;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String value) {
        this.longitude = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String value) {
        this.user = value;
    }

    public String getDivision() {
        return this.division;
    }

    public void setDivision(String value) {
        this.division = value;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String value) {
        this.district = value;
    }

    public String getUpazilla() {
        return this.upazilla;
    }

    public void setUpazilla(String value) {
        this.upazilla = value;
    }

    @Override
    public String toString() {
        return "Send_Delivery_address{" +
                "receiversName='" + receiversName + '\'' +
                ", receiversContact='" + receiversContact + '\'' +
                ", address='" + address + '\'' +
                ", village='" + village + '\'' +
                ", nearbyLandmark='" + nearbyLandmark + '\'' +
                ", profession='" + profession + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", status='" + status + '\'' +
                ", user='" + user + '\'' +
                ", division='" + division + '\'' +
                ", district='" + district + '\'' +
                ", upazilla='" + upazilla + '\'' +
                '}';
    }
}
