package com.gtechnologies.fishbangla.API_SEND;

public class SEND_AUCTION_AMOUNT {

    String auction_id;

    String user_id;

    String amount;

    public SEND_AUCTION_AMOUNT(String auction_id, String user_id, String amount) {
        this.auction_id = auction_id;
        this.user_id = user_id;
        this.amount = amount;
    }

    public String getAuction_id() {
        return this.auction_id;
    }

    public void setAuction_id(String value) {
        this.auction_id = value;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String value) {
        this.user_id = value;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String value) {
        this.amount = value;
    }

    @Override
    public String toString() {
        return "SEND_AUCTION_AMOUNT{" +
                "auction_id='" + auction_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}