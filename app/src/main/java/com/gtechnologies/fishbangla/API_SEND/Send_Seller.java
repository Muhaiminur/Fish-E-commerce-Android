package com.gtechnologies.fishbangla.API_SEND;

public class Send_Seller {
    String seller_id;

    public Send_Seller(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_id() {
        return this.seller_id;
    }

    public void setSeller_id(String value) {
        this.seller_id = value;
    }

    @Override
    public String toString() {
        return "Send_Seller{" +
                "seller_id='" + seller_id + '\'' +
                '}';
    }
}
