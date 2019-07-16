package com.gtechnologies.fishbangla.API_GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_MAX_AUCTION{

    @SerializedName("auctionImage")
    @Expose
    private String auctionImage;
    @SerializedName("participantUserId")
    @Expose
    private Integer participantUserId;
    @SerializedName("participantPicture")
    @Expose
    private String participantPicture;
    @SerializedName("participantExpire")
    @Expose
    private String participantExpire;
    @SerializedName("auctioneerContact")
    @Expose
    private String auctioneerContact;
    @SerializedName("auctioneerId")
    @Expose
    private Integer auctioneerId;
    @SerializedName("auctionName")
    @Expose
    private String auctionName;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("auctionAmount")
    @Expose
    private Integer auctionAmount;
    @SerializedName("auctionExpireTime")
    @Expose
    private String auctionExpireTime;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("participantName")
    @Expose
    private String participantName;
    @SerializedName("auctioneerName")
    @Expose
    private String auctioneerName;
    @SerializedName("auctioneerPicture")
    @Expose
    private String auctioneerPicture;
    @SerializedName("participantContact")
    @Expose
    private String participantContact;

    public String getAuctionImage() {
        return auctionImage;
    }

    public void setAuctionImage(String auctionImage) {
        this.auctionImage = auctionImage;
    }

    public Integer getParticipantUserId() {
        return participantUserId;
    }

    public void setParticipantUserId(Integer participantUserId) {
        this.participantUserId = participantUserId;
    }

    public String getParticipantPicture() {
        return participantPicture;
    }

    public void setParticipantPicture(String participantPicture) {
        this.participantPicture = participantPicture;
    }

    public String getParticipantExpire() {
        return participantExpire;
    }

    public void setParticipantExpire(String participantExpire) {
        this.participantExpire = participantExpire;
    }

    public String getAuctioneerContact() {
        return auctioneerContact;
    }

    public void setAuctioneerContact(String auctioneerContact) {
        this.auctioneerContact = auctioneerContact;
    }

    public Integer getAuctioneerId() {
        return auctioneerId;
    }

    public void setAuctioneerId(Integer auctioneerId) {
        this.auctioneerId = auctioneerId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAuctionAmount() {
        return auctionAmount;
    }

    public void setAuctionAmount(Integer auctionAmount) {
        this.auctionAmount = auctionAmount;
    }

    public String getAuctionExpireTime() {
        return auctionExpireTime;
    }

    public void setAuctionExpireTime(String auctionExpireTime) {
        this.auctionExpireTime = auctionExpireTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
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

    public String getParticipantContact() {
        return participantContact;
    }

    public void setParticipantContact(String participantContact) {
        this.participantContact = participantContact;
    }

    @Override
    public String toString() {
        return "GET_MAX_AUCTION{" +
                "auctionImage='" + auctionImage + '\'' +
                ", participantUserId=" + participantUserId +
                ", participantPicture='" + participantPicture + '\'' +
                ", participantExpire='" + participantExpire + '\'' +
                ", auctioneerContact='" + auctioneerContact + '\'' +
                ", auctioneerId=" + auctioneerId +
                ", auctionName='" + auctionName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", auctionAmount=" + auctionAmount +
                ", auctionExpireTime='" + auctionExpireTime + '\'' +
                ", id=" + id +
                ", participantName='" + participantName + '\'' +
                ", auctioneerName='" + auctioneerName + '\'' +
                ", auctioneerPicture='" + auctioneerPicture + '\'' +
                ", participantContact='" + participantContact + '\'' +
                '}';
    }
}
