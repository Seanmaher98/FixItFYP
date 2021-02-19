package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.BookingsUsers;
import com.example.fixitfyp.ViewHolder.UserBookingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserBookings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnClose;

    DatabaseReference UserBookingRef;
    String uid;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bookings);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        btnClose = findViewById(R.id.btnClose);

        UserBookingRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Bookings");

        recyclerView = findViewById(R.id.recycler_bookings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    //This method is adapted from the above mentioned video
    //(https://youtu.be/745ElNRjJew) Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe
    //START OF YOUTUBE CODE
    @Override
    protected void onStart() {
        super.onStart();
        //This recycler view is a firebase recycler view, it is used to pull the data from my database.
        //The code uses my database reference to pull data from my "Trades" database path
        FirebaseRecyclerOptions<BookingsUsers> options =
                new FirebaseRecyclerOptions.Builder<BookingsUsers>()
                .setQuery(UserBookingRef, BookingsUsers.class).build();

        FirebaseRecyclerAdapter<BookingsUsers, UserBookingViewHolder> adapter =
                new FirebaseRecyclerAdapter<BookingsUsers, UserBookingViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserBookingViewHolder userBookingViewHolder, int i, @NonNull BookingsUsers bookingsUsers) {
                        userBookingViewHolder.txtTradeName.setText("Tradesman: " + bookingsUsers.getTradeName());
                        userBookingViewHolder.txtDate.setText("Booking Date: " + bookingsUsers.getBookingDate());
                        userBookingViewHolder.txtBookingId.setText(bookingsUsers.getBookingId());
                        userBookingViewHolder.txtTradeId.setText(bookingsUsers.getTradeId());

                        userBookingViewHolder.btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Allows the tradeID and bookingID to be reused in CancelBookingActivity
                                //This is necessary to get the path for deletion in Firebase
                                Intent intent = new Intent(UserBookings.this, CancelBookingActivity.class);
                                intent.putExtra("tradeId", bookingsUsers.getTradeId());
                                intent.putExtra("bookingId", bookingsUsers.getBookingId());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public UserBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_booking_items_layout, parent, false);
                        UserBookingViewHolder holder = new UserBookingViewHolder(view);
                        return holder;
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        //END OF YOUTUBE CODE
    }

}