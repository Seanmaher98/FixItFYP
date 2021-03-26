package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Dialogs.DeleteDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CancelBookingActivity extends AppCompatActivity {
    //This Activity appears when cancel button is clicked on my user in "My Bookings" (UserBookingsActivity)
    private Button btnDelete, btnClose, btnReview, btnMessage;
    private TextView txtCurrentView;
    private String tradeID = "";
    private String bookingID = "";
    private String bookingDate = "";
    private String tradeName = "";
    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        btnReview = findViewById(R.id.button_review);
        btnDelete = findViewById(R.id.button_Delete);
        btnClose = findViewById(R.id.btnClose);
        btnMessage = findViewById(R.id.button_message);
        txtCurrentView = findViewById(R.id.current_view);
        //Allows the tradeID and bookingID from UserBookings to be reused here
        //This is necessary to get the path for deletion in Firebase
        tradeID = getIntent().getStringExtra("tradeId");
        bookingID = getIntent().getStringExtra("bookingId");
        tradeName = getIntent().getStringExtra("tradeName");
        bookingDate = getIntent().getStringExtra("bookingDate");

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        txtCurrentView.setText(tradeName + " booked for " + bookingDate);


        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CancelBookingActivity.this, UserReviewActivity.class);
                intent.putExtra("tradeId", tradeID);
                intent.putExtra("tradeName", tradeName);
                intent.putExtra("bookingId", bookingID);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBooking(bookingID);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CancelBookingActivity.this, UserMessages.class);
                intent.putExtra("tradeId", tradeID);
                intent.putExtra("tradeName", tradeName);
                startActivity(intent);
            }
        });
    }

    private void openDialog() {
        DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.show(getSupportFragmentManager(), "Delete Dialog");
    }

    //This function deletes the selected Booking from the users and trades paths in Firebase
    private void deleteBooking(String bookingID) {
            DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("Users").
                    child(uid).child("Bookings").child(bookingID);
            DatabaseReference drTrade = FirebaseDatabase.getInstance().getReference("Trades").
                    child(tradeID).child("Bookings").child(bookingID);

            drUser.removeValue();
            drTrade.removeValue();

            openDialog();
    }
}