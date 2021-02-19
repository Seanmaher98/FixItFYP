package com.example.fixitfyp.Model;
//This class is used for displaying the images from firebase
//At the moment the images upload successfully, issue with displaying them
//ToDo Get images from firebase to display
public class Images {
    private String imageUrl;

    public Images(){

    }

    public Images(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
