package com.example.fixitfyp.Model;
//This class is used for displaying the booking details in the users my bookings section
public class Reviews {

    private String reviewId, review;

    public Reviews() {

    }

    public Reviews(String reviewId, String review) {
        this.reviewId = reviewId;
        this.review = review;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}


