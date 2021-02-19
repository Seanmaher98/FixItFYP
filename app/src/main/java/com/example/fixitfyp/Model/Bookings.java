package com.example.fixitfyp.Model;
//This class is used for displaying the booking details on the Trade Dashboard
//ToDo Show more than just username and booking date
public class Bookings {

    private String userEmail, BookingDate;

    public Bookings() {

    }
    public Bookings(String userEmail, String bookingDate) {
        this.userEmail = userEmail;
        BookingDate = bookingDate;
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
