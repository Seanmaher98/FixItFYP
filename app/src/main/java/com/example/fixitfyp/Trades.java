package com.example.fixitfyp;

//Creating Variables for the data inputted by Service Provider Signing Up
//Code here was obtained and modified from youtube tutorial
//YouTube Tutorial, Firebase Realtime Database Tutorial for Android, Simplified Coding - https://youtu.be/EM2x33g4syY
public class Trades {
    String tradeId;
    String tradeName;
    String tradeEmail;
    String tradePassword;
    String tradeJob;
    String tradePhone;

 public Trades(String Id, String Name, String Email, String Phone, String Job) {
     this.tradeId = Id;
     this.tradeName = Name;
     this.tradeEmail = Email;
     this.tradePhone = Phone;
     this.tradeJob = Job;

 }

 public Trades(){

 }

    public String getTradeId() {
        return tradeId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public String getTradeEmail() {
        return tradeEmail;
    }

    public String getTradePassword() {
        return tradePassword;
    }

    public String getTradePhone() {
        return tradePhone;
    }

    public String getTradeJob() {
        return tradeJob;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public void setTradeEmail(String tradeEmail) {
        this.tradeEmail = tradeEmail;
    }

    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    public void setTradePhone(String tradePhone) {
        this.tradePhone = tradePhone;
    }

    public void setTradeJob(String tradeJob) {
        this.tradeJob = tradeJob;
    }
}



