package com.example.fixitfyp;

//Creating Variables for the data inputted by Service Provider Signing Up
//Code here was obtained and modified from youtube video on connecting Firebase to Android
//YouTube Tutorial - https://youtu.be/EM2x33g4syY
public class Trades {
    String tradeId;
    String tradeFirstName;
    String tradeLastName;
    String tradeAddressLine1;
    String tradeAddressLine2;
    String tradeAddressLineTown;
    String tradeCounty;
    String tradeJobs;

    public Trades() {

    }

    public Trades(String tradeId, String tradeFirstName, String tradeLastName, String tradeAddressLine1,
                  String tradeAddressLine2, String tradeAddressLineTown, String tradeCounty, String tradeGenre) {
        this.tradeId = tradeId;
        this.tradeFirstName = tradeFirstName;
        this.tradeLastName = tradeLastName;
        this.tradeAddressLine1 = tradeAddressLine1;
        this.tradeAddressLine2 = tradeAddressLine2;
        this.tradeAddressLineTown = tradeAddressLineTown;
        this.tradeCounty = tradeCounty;
        this.tradeJobs = tradeGenre;
    }

    public String getTradeId() {
        return tradeId;
    }

    public String getTradeFirstName() {
        return tradeFirstName;
    }

    public String getTradeLastName() {
        return tradeLastName;
    }

    public String getTradeAddressLine1() {
        return tradeAddressLine1;
    }

    public String getTradeAddressLine2() {
        return tradeAddressLine2;
    }

    public String getTradeAddressLineTown() {
        return tradeAddressLineTown;
    }

    public String getTradeCounty() {
        return tradeCounty;
    }

    public String getTradeJobs() { return tradeJobs;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setTradeFirstName(String tradeFirstName) {
        this.tradeFirstName = tradeFirstName;
    }

    public void setTradeLastName(String tradeLastName) {
        this.tradeLastName = tradeLastName;
    }

    public void setTradeAddressLine1(String tradeAddressLine1) {
        this.tradeAddressLine1 = tradeAddressLine1;
    }

    public void setTradeAddressLine2(String tradeAddressLine2) {
        this.tradeAddressLine2 = tradeAddressLine2;
    }

    public void setTradeAddressLineTown(String tradeAddressLineTown) {
        this.tradeAddressLineTown = tradeAddressLineTown;
    }

    public void setTradeCounty(String tradeCounty) {
        this.tradeCounty = tradeCounty;
    }

    public void setTradeJobs(String tradeGenre) {
        this.tradeJobs = tradeJobs;
    }



}
//End

