package com.gtechnologies.fishbangla.API_SEND;

public class Send_Product_List {

    int productListId;

    public Send_Product_List() {
    }

    public Send_Product_List(int productListId) {
        this.productListId = productListId;
    }

    @Override
    public String toString() {
        return "Send_Product_List{" +
                "productListId=" + productListId + "}";
    }
}
