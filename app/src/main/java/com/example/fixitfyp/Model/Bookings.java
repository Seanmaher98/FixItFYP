package com.example.fixitfyp.Model;
//This class is used for displaying the booking details on the Trade Dashboard
//ToDo Show more than just username and booking date
public class Bookings {

    private String userId, userName, userEmail, BookingDate, bookingId;

    public Bookings() {

    }
    public Bookings(String userId, String userName, String userEmail, String bookingDate, String bookingId) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        BookingDate = bookingDate;
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
