package com.example.fixitfyp.Model;

public class Products{
    //Products class used for retrieving data from firebase and allows me to show them to user
    //Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe, https://youtu.be/745ElNRjJew
    //It is for this reason that it is labelled products as opposed to trades
    //Used for HomeActivity and UserNavigationActivity
    private String tradeName, tradeEmail, tradeId, tradeJob, tradePhone, imageUrl;
    private int  tradePrice1, tradePrice2;


    public Products() {
    }

    public Products(String tradeName, String tradeEmail, String tradeId,
                    String tradeJob, String tradePhone , String imageUrl, int tradePrice1, int tradePrice2) {
        this.tradeName = tradeName;
        this.tradeEmail = tradeEmail;
        this.tradeId = tradeId;
        this.tradeJob = tradeJob;
        this.tradePhone = tradePhone;
        this.imageUrl = imageUrl;
        this.tradePrice1 = tradePrice1;
        this.tradePrice2 = tradePrice2;


    }

    public int getTradePrice1() {
        return tradePrice1;
    }

    public void setTradePrice1(int tradePrice1) {
        this.tradePrice1 = tradePrice1;
    }

    public int getTradePrice2() {
        return tradePrice2;
    }

    public void setTradePrice2(int tradePrice2) {
        this.tradePrice2 = tradePrice2;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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