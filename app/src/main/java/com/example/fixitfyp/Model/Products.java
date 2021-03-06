package com.example.fixitfyp.Model;

public class Products{
    //Products class used for retrieving data from firebase and allows me to show them to user
    //Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe, https://youtu.be/745ElNRjJew
    //It is for this reason that it is labelled products as opposed to trades
    //Used for HomeActivity and UserNavigationActivity
    private String tradeName, tradeEmail, tradeId, tradeJob, tradePhone, tradeImage, tradeCounty;


    public Products() {
    }

    public Products( String tradeName, String tradeEmail, String tradeId,
                    String tradeJob, String tradePhone , String tradeImage, String tradeCounty) {

        this.tradeName = tradeName;
        this.tradeEmail = tradeEmail;
        this.tradeId = tradeId;
        this.tradeJob = tradeJob;
        this.tradePhone = tradePhone;
        this.tradeImage = tradeImage;
        this.tradeCounty = tradeCounty;



    }

    public String getTradeCounty() {
        return tradeCounty;
    }

    public void setTradeCounty(String tradeCounty) {
        this.tradeCounty = tradeCounty;
    }

    public String getTradeImage() {
        return tradeImage;
    }

    public void setTradeImage(String tradeImage) {
        this.tradeImage = tradeImage;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeEmail() {
        return tradeEmail;
    }

    public void setTradeEmail(String tradeEmail) {
        this.tradeEmail = tradeEmail;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeJob() {
        return tradeJob;
    }

    public void setTradeJob(String tradeJob) {
        this.tradeJob = tradeJob;
    }

    public String getTradePhone() {
        return tradePhone;
    }

    public void setTradePhone(String tradePhone) {
        this.tradePhone = tradePhone;
    }

}