package com.gtechnologies.fishbangla.API_SEND;

import com.google.gson.annotations.SerializedName;

public class Send_Suggestion {
    String productId;

    String saleType;

    public Send_Suggestion(String productId, String saleType) {
        this.productId = productId;
        this.saleType = saleType;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String value) {
        this.productId = value;
    }

    public String getSaleType() {
        return this.saleType;
    }

    public void setSaleType(String value) {
        this.saleType = value;
    }

    @Override
    public String toString() {
        return "Send_Suggestion{" +
                "productId=" + productId +
                ", saleType='" + saleType + '\'' +
                '}';
    }
}
