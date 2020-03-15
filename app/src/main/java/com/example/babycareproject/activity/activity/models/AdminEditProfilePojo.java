package com.example.babycareproject.activity.activity.models;

import com.google.gson.annotations.SerializedName;

public class AdminEditProfilePojo {
    @SerializedName("name")
    private String name ;

    @SerializedName("phone")
    private String phone ;

    @SerializedName("emailid")
    private String emailid ;


    @SerializedName("pwd")
    private String pwd ;

    @SerializedName("experience")
    private String experience ;

    @SerializedName("location")
    private String location ;

    @SerializedName("img_url")
    private String img_url ;

    AdminEditProfilePojo(String name, String phone, String emailid, String pwd,String experience,String location,String img_url){

        this.setName(name);
        this.setPhone(phone);
        this.setEmailid(emailid);
        this.setPwd(pwd);
        this.setExperience(experience);
        this.setLocation(location);
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
