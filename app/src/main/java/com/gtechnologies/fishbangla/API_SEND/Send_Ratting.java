package com.gtechnologies.fishbangla.API_SEND;

public class Send_Ratting {
    String sellerId;

    String buyerId;

    int rating;

    String review;

    String status;

    public Send_Ratting(String sellerId, String buyerId, int rating, String review, String status) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.review = review;
        this.status = status;
    }

    public String getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(String value) {
        this.sellerId = value;
    }

    public String getBuyerId() {
        return this.buyerId;
    }

    public void setBuyerId(String value) {
        this.buyerId = value;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int value) {
        this.rating = value;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String value) {
        this.review = value;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    @Override
    public String toString() {
        return "Send_Ratting{" +
                "sellerId='" + sellerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", rating='" + rating + '\'' +
                ", review='" + review + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
