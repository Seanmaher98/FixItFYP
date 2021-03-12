package com.example.fixitfyp.Model;
//This class is used for displaying the booking details in the users my bookings section
public class ProductReviews {

    private String reviewId, review;

    public ProductReviews() {

    }

    public ProductReviews(String reviewId, String review) {
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


