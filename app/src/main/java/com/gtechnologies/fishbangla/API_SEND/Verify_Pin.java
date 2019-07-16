package com.gtechnologies.fishbangla.API_SEND;

@SuppressWarnings("all")
public class Verify_Pin {
    private final String userMobile;

    private final String userInput;

    public Verify_Pin(String userMobile, String userInput) {
        this.userMobile = userMobile;
        this.userInput = userInput;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserInput() {
        return userInput;
    }

    @Override
    public String toString() {
        return "Verify_Pin{" +
                "userMobile='" + userMobile + '\'' +
                ", userInput='" + userInput + '\'' +
                '}';
    }
}
