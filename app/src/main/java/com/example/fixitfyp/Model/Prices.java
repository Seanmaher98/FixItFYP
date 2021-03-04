package com.example.fixitfyp.Model;

public class Prices {
    //Products class used for retrieving data from firebase and allows me to show them to user
    //Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe, https://youtu.be/745ElNRjJew
    //It is for this reason that it is labelled products as opposed to trades
    //Used for HomeActivity and UserNavigationActivity

    private String tradePrice1, tradePrice2, tradePrice3, tradePrice4;

    public Prices() {
    }

    public Prices(String tradePrice1, String tradePrice2, String tradePrice3, String tradePrice4) {
        this.tradePrice1 = tradePrice1;
        this.tradePrice2 = tradePrice2;
        this.tradePrice3 = tradePrice3;
        this.tradePrice4 = tradePrice4;
    }

    public String getTradePrice1() {
        return tradePrice1;
    }

    public void setTradePrice1(String tradePrice1) {
        this.tradePrice1 = tradePrice1;
    }

    public String getTradePrice2() {
        return tradePrice2;
    }

    public void setTradePrice2(String tradePrice2) {
        this.tradePrice2 = tradePrice2;
    }

    public String getTradePrice3() {
        return tradePrice3;
    }

    public void setTradePrice3(String tradePrice3) {
        this.tradePrice3 = tradePrice3;
    }

    public String getTradePrice4() {
        return tradePrice4;
    }

    public void setTradePrice4(String tradePrice4) {
        this.tradePrice4 = tradePrice4;
    }
}