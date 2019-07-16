package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("productName")
    @Expose
    private Integer productName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getProductName() {
        return productName;
    }

    public void setProductName(Integer productName) {
        this.productName = productName;
    }

}