package com.example.fixitfyp.Model;

public class Data {

    String prodName;
    String prodEmail;

    public Data(){

    }

    public Data(String prodName, String prodEmail) {
        this.prodName = prodName;
        this.prodEmail = prodEmail;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdEmail() {
        return prodEmail;
    }

    public void setProdEmail(String prodEmail) {
        this.prodEmail = prodEmail;
    }
}

