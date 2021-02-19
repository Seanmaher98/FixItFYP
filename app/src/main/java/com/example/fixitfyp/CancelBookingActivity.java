package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CancelBookingActivity extends AppCompatActivity {
    //This Activity appears when cancel button is clicked on my user in "My Bookings" (UserBookingsActivity)
    private Button btnDelete, btnClose;
    private String tradeID = "";
    private String bookingID = "";
    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);

        btnDelete = findViewById(R.id.button_Delete);
        btnClose = findViewById(R.id.btnClose);
        //Allows the tradeID and bookingID from UserBookings to be reused here
        //This is necessary to get the path for deletion in Firebase
        tradeID = getIntent().getStringExtra("tradeId");
        bookingID = getIntent().getStringExtra("bookingId");

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBooking(bookingID);
                finish();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //This function deletes the selected Booking from the users and trades paths in Firebase
    private void deleteBooking(String bookingID) {
            DatabaseReference drUser = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Bookings").child(bookingID);
            DatabaseReference drTrade = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Bookings").child(bookingID);

            drUser.removeValue();
            drTrade.removeValue();

            Toast.makeText(this, "Booking Deleted", Toast.LENGTH_LONG).show();
    }
}