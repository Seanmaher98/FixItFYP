package com.example.fixitfyp.Model;

public class Products {

    private String tradeName, tradeEmail, tradeId, tradeJob, tradePhone;

    public Products(){

    }

    public Products(String tradeName, String tradeEmail, String tradeId, String tradeJob, String tradePhone) {
        this.tradeName = tradeName;
        this.tradeEmail = tradeEmail;
        this.tradeId = tradeId;
        this.tradeJob = tradeJob;
        this.tradePhone = tradePhone;
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
