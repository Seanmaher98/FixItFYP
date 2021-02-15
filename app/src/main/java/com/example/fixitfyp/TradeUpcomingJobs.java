package com.example.fixitfyp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.Bookings;
import com.example.fixitfyp.ViewHolder.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TradeUpcomingJobs extends AppCompatActivity {


    private RecyclerView recyclerView;
    DatabaseReference BookingRef;
    DatabaseReference TradeRef;
    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_upcoming_jobs);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        BookingRef = FirebaseDatabase.getInstance().getReference("Trades").child(uid).child("Bookings");

        recyclerView = findViewById(R.id.recycler_jobs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    //This method is adapted from the above mentioned video
    //(https://youtu.be/745ElNRjJew) Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe
    //START OF YOUTUBE CODE
    @Override
    protected void onStart() {
        super.onStart();
        //This recycler view is a firebase recycler view, it is used to pull the data from my database.
        //The code uses my database reference to pull data from my "Trades" database path
        FirebaseRecyclerOptions<Bookings> options =
                new FirebaseRecyclerOptions.Builder<Bookings>()
                .setQuery(BookingRef, Bookings.class).build();
        FirebaseRecyclerAdapter<Bookings, UserViewHolder> adapter =
                new FirebaseRecyclerAdapter<Bookings, UserViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull Bookings bookings) {
                        userViewHolder.txtUserName.setText(bookings.getUserName());
                    }

                    @NonNull
                    @Override
                    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_items_layout, parent, false);
                        UserViewHolder holder = new UserViewHolder(view);
                        return holder;
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        //END OF YOUTUBE CODE
    }
}