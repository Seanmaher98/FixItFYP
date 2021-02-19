package com.example.fixitfyp.Model;
//This class is used for displaying the booking details in the users my bookings section
public class BookingsUsers {

    private String tradeName, BookingDate, bookingId, tradeId;

    public BookingsUsers() {

    }

    public BookingsUsers(String tradeName, String bookingDate, String bookingId, String tradeId) {
        this.tradeName = tradeName;
        BookingDate = bookingDate;
        this.bookingId = bookingId;
        this.tradeId = tradeId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }
}


