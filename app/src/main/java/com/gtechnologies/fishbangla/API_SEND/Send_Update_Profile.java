package com.gtechnologies.fishbangla.API_SEND;

public class Send_Update_Profile{

    String user_id;

    String refer_code;

    String contact;

    String name;

    String reference_code;

    String brefere_code;

    String email;

    public Send_Update_Profile(String user_id, String refer_code, String contact, String name, String reference_code, String brefere_code, String email) {
        this.user_id = user_id;
        this.refer_code = refer_code;
        this.contact = contact;
        this.name = name;
        this.reference_code = reference_code;
        this.brefere_code = brefere_code;
        this.email = email;
    }

    public Send_Update_Profile() {
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String value) {
        this.user_id = value;
    }

    public String getRefer_code() {
        return this.refer_code;
    }

    public void setRefer_code(String value) {
        this.refer_code = value;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String value) {
        this.contact = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getReference_code() {
        return this.reference_code;
    }

    public void setReference_code(String value) {
        this.reference_code = value;
    }

    public String getBrefere_code() {
        return this.brefere_code;
    }

    public void setBrefere_code(String value) {
        this.brefere_code = value;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    @Override
    public String toString() {
        return "Send_Update_Profile{" +
                "user_id='" + user_id + '\'' +
                ", refer_code='" + refer_code + '\'' +
                ", contact='" + contact + '\'' +
                ", name='" + name + '\'' +
                ", reference_code='" + reference_code + '\'' +
                ", brefere_code='" + brefere_code + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}