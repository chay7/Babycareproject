package com.example.babycareproject.activity.activity.models;

import com.google.gson.annotations.SerializedName;

public class EditProfilePojo {
    @SerializedName("name")
    private String name ;

    @SerializedName("phone")
    private
    String phone ;

    @SerializedName("emailid")
    private
    String emailid ;



    @SerializedName("pwd")
    private
    String pwd ;

    @SerializedName("img_url")
    private String img_url ;




    EditProfilePojo(String name, String phone, String emailid, String pwd,String img_url){

        this.setName(name);
        this.setPhone(phone);
        this.setEmailid(emailid);
        this.setPwd(pwd);
        this.setImg_url(img_url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
