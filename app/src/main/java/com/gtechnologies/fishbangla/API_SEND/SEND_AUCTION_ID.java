package com.gtechnologies.fishbangla.API_SEND;

public class SEND_AUCTION_ID{

    String auctionId;

    public SEND_AUCTION_ID(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionId() {
        return this.auctionId;
    }

    public void setAuctionId(String value) {
        this.auctionId = value;
    }

    @Override
    public String toString() {
        return "SEND_AUCTION_ID{" +
                "auctionId='" + auctionId + '\'' +
                '}';
    }
}