package com.gtechnologies.fishbangla.Model;

import java.io.Serializable;

public class Auction_List implements Serializable {
    String image;
    String createdAt;
    int amount;
    String expireTime;
    String name;
    String auctioneerContact;
    int id;
    int auctioneerId;
    String auctioneerName;
    String auctioneerPicture;

    public Auction_List() {
    }

    public Auction_List(String image, String createdAt, int amount, String expireTime, String name, String auctioneerContact, int id, int auctioneerId, String auctioneerName, String auctioneerPicture) {
        this.image = image;
        this.createdAt = createdAt;
        this.amount = amount;
        this.expireTime = expireTime;
        this.name = name;
        this.auctioneerContact = auctioneerContact;
        this.id = id;
        this.auctioneerId = auctioneerId;
        this.auctioneerName = auctioneerName;
        this.auctioneerPicture = auctioneerPicture;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuctioneerContact() {
        return auctioneerContact;
    }

    public void setAuctioneerContact(String auctioneerContact) {
        this.auctioneerContact = auctioneerContact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuctioneerId() {
        return auctioneerId;
    }

    public void setAuctioneerId(int auctioneerId) {
        this.auctioneerId = auctioneerId;
    }

    public String getAuctioneerName() {
        return auctioneerName;
    }

    public void setAuctioneerName(String auctioneerName) {
        this.auctioneerName = auctioneerName;
    }

    public String getAuctioneerPicture() {
        return auctioneerPicture;
    }

    public void setAuctioneerPicture(String auctioneerPicture) {
        this.auctioneerPicture = auctioneerPicture;
    }

    /*@Override
    public String toString() {
        return "Auction_List{" +
                "image_url='" + image_url + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                ", max_price='" + max_price + '\'' +
                '}';
    }*/

}
