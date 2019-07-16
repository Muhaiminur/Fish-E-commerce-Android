package com.gtechnologies.fishbangla.API_SEND;

public class SEND_TAG {

    String saleType;

    String tagId;

    public SEND_TAG(String saleType, String tagId) {
        this.saleType = saleType;
        this.tagId = tagId;
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

    @Override
    public String toString() {
        return "SEND_TAG{" +
                "saleType='" + saleType + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}