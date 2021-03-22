package com.example.fixitfyp.Model;
//This class is used for displaying the images from firebase

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
