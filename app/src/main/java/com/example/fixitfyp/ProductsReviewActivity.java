package com.example.fixitfyp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.Reviews;
import com.example.fixitfyp.ViewHolder.ReviewViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductsReviewActivity extends AppCompatActivity {

    private String tradeID = "";
    private String tradeName = "";
    private Button closeButton;
    RecyclerView recyclerView;
    DatabaseReference tradeReviewRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_review);

        tradeReviewRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Reviews");

        recyclerView = findViewById(R.id.recycler_product_review);
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
        FirebaseRecyclerOptions<Reviews> options =
                new FirebaseRecyclerOptions.Builder<Reviews>()
                        .setQuery(tradeReviewRef, Reviews.class).build();

        FirebaseRecyclerAdapter<Reviews, ReviewViewHolder> adapter =
                new FirebaseRecyclerAdapter<Reviews, ReviewViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i, @NonNull Reviews reviews) {
                        reviewViewHolder.txtReview.setText(reviews.getReview());
                    }

                    @NonNull
                    @Override
                    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_review_layout, parent, false);
                        ReviewViewHolder holder = new ReviewViewHolder(view);
                        return holder;
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        //END OF YOUTUBE CODE
    }
}