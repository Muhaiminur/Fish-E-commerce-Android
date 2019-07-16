package com.gtechnologies.fishbangla.API_SEND;

public class Send_UserID {
    String userId;

    public Send_UserID(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String value) {
        this.userId = value;
    }

    @Override
    public String toString() {
        return "Send_UserID{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
