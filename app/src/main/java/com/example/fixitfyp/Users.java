package com.example.fixitfyp;

//Creating Variables for the data inputted by Users Signing Up
//Code here was obtained and modified from youtube tutorial
//YouTube Tutorial, Firebase Realtime Database Tutorial for Android, Simplified Coding - https://youtu.be/EM2x33g4syY
public class Users {
    String userId;
    String userFirstName;
    String userLastName;
    String userEmail;
    String userAddressLine1;
    String userAddressLine2;
    String userAddressLineTown;
    String userCounty;

    public Users() {

    }

    public Users(String userId, String userFirstName, String userLastName, String userEmail, String userAddressLine1,
                 String userAddressLine2, String userAddressLineTown, String userCounty) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userAddressLine1 = userAddressLine1;
        this.userAddressLine2 = userAddressLine2;
        this.userAddressLineTown = userAddressLineTown;
        this.userCounty = userCounty;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddressLine1() {
        return userAddressLine1;
    }

    public String getUserAddressLine2() {
        return userAddressLine2;
    }

    public String getUserAddressLineTown() {
        return userAddressLineTown;
    }

    public String getUserCounty() {
        return userCounty;
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAddressLine1(String userAddressLine1) {
        this.userAddressLine1 = userAddressLine1;
    }

    public void setUserAddressLine2(String userAddressLine2) {
        this.userAddressLine2 = userAddressLine2;
    }

    public void setUserAddressLineTown(String userAddressLineTown) {
        this.userAddressLineTown = userAddressLineTown;
    }

    public void setUserCounty(String userCounty) {
        this.userCounty = userCounty;
    }

}
//End of Youtube Code

