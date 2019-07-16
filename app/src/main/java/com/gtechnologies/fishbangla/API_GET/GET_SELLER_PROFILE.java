package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_SELLER_PROFILE {
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("sellerId")
    @Expose
    private Integer sellerId;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("sellerPicture")
    @Expose
    private String sellerPicture;
    @SerializedName("buyerPicture")
    @Expose
    private String buyerPicture;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("sellerName")
    @Expose
    private String sellerName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("buyerName")
    @Expose
    private String buyerName;
    @SerializedName("buyerId")
    @Expose
    private Integer buyerId;
    @SerializedName("sellerContact")
    @Expose
    private String sellerContact;
    @SerializedName("buyerContact")
    @Expose
    private String buyerContact;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getSellerPicture() {
        return sellerPicture;
    }

    public void setSellerPicture(String sellerPicture) {
        this.sellerPicture = sellerPicture;
    }

    public String getBuyerPicture() {
        return buyerPicture;
    }

    public void setBuyerPicture(String buyerPicture) {
        this.buyerPicture = buyerPicture;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerContact() {
        return sellerContact;
    }

    public void setSellerContact(String sellerContact) {
        this.sellerContact = sellerContact;
    }

    public String getBuyerContact() {
        return buyerContact;
    }

    public void setBuyerContact(String buyerContact) {
        this.buyerContact = buyerContact;
    }
}
