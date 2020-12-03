package com.example.fixitfyp;

//Creating Variables for the data inputted by Users Signing Up
//Code here was obtained and modified from youtube tutorial
//YouTube Tutorial, Firebase Realtime Database Tutorial for Android, Simplified Coding - https://youtu.be/EM2x33g4syY
public class Users{
    String userId;
    String userName;
    String userEmail;
    String userPhone;


    public Users(String userId, String userName, String userEmail, String userPhone) {
            this.userId = userId;
            this.userName = userName;
            this.userEmail = userEmail;
            this.userPhone = userPhone;
    }

    public Users() {
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}

