package com.gtechnologies.fishbangla.API_SEND;

public class Send_Product {
    String productListId;

    String description;

    String quantity;

    String minQty;

    String price;

    String available;

    String frozen;

    String readyFood;

    String saleType;

    String tagId;

    String userId;

    String upazillaId;

    public Send_Product(String productListId, String description, String quantity, String minQty, String price, String available, String frozen, String readyFood, String saleType, String tagId, String userId, String upazillaId) {
        this.productListId = productListId;
        this.description = description;
        this.quantity = quantity;
        this.minQty = minQty;
        this.price = price;
        this.available = available;
        this.frozen = frozen;
        this.readyFood = readyFood;
        this.saleType = saleType;
        this.tagId = tagId;
        this.userId = userId;
        this.upazillaId = upazillaId;
    }

    public String getProductListId() {
        return this.productListId;
    }

    public void setProductListId(String value) {
        this.productListId = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String value) {
        this.quantity = value;
    }

    public String getMinQty() {
        return this.minQty;
    }

    public void setMinQty(String value) {
        this.minQty = value;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String value) {
        this.price = value;
    }

    public String getAvailable() {
        return this.available;
    }

    public void setAvailable(String value) {
        this.available = value;
    }

    public String getFrozen() {
        return this.frozen;
    }

    public void setFrozen(String value) {
        this.frozen = value;
    }

    public String getReadyFood() {
        return this.readyFood;
    }

    public void setReadyFood(String value) {
        this.readyFood = value;
    }

    public String getSaleType() {
        return this.saleType;
    }

    public void setSaleType(String value) {
        this.saleType = value;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String value) {
        this.tagId = value;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    public String getUpazillaId() {
        return this.upazillaId;
    }

    public void setUpazillaId(String value) {
        this.upazillaId = value;
    }

    @Override
    public String toString() {
        return "Send_Product{" +
                "productListId='" + productListId + '\'' +
                ", description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                ", minQty='" + minQty + '\'' +
                ", price='" + price + '\'' +
                ", available='" + available + '\'' +
                ", frozen='" + frozen + '\'' +
                ", readyFood='" + readyFood + '\'' +
                ", saleType='" + saleType + '\'' +
                ", tagId='" + tagId + '\'' +
                ", userId='" + userId + '\'' +
                ", upazillaId='" + upazillaId + '\'' +
                '}';
    }
}
