package com.gtechnologies.fishbangla.API_SEND;

public class Send_List {
    String filter;

    String sale_type;

    String tagId;

    public Send_List(String filter, String sale_type, String tagId) {
        this.filter = filter;
        this.sale_type = sale_type;
        this.tagId = tagId;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String value) {
        this.filter = value;
    }

    public String getSale_type() {
        return this.sale_type;
    }

    public void setSale_type(String value) {
        this.sale_type = value;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String value) {
        this.tagId = value;
    }

    @Override
    public String toString() {
        return "Send_List{" +
                "filter='" + filter + '\'' +
                ", sale_type='" + sale_type + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
