package com.example.fixitfyp.Model;

public class Messages {
    //Products class used for retrieving data from firebase and allows me to show them to user
    //Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe, https://youtu.be/745ElNRjJew
    //It is for this reason that it is labelled products as opposed to trades
    //Used for HomeActivity and UserNavigationActivity
    private String message, messageId, tradeId, userId, userName;


    public Messages() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}