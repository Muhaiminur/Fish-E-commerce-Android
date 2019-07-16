package com.gtechnologies.fishbangla.API_SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable {

    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;

    public Item(Integer productId, String comment, String quantity, String amount, String status) {
        this.productId = productId;
        this.comment = comment;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
    }

    public Item() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "productId=" + productId +
                ", comment='" + comment + '\'' +
                ", quantity='" + quantity + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}