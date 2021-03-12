package com.example.fixitfyp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.ProductReviews;
import com.example.fixitfyp.ViewHolder.ProductReviewViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductsReviewActivity extends AppCompatActivity {

    private String tradeID = "";
    private String tradeName = "";
    private Button closeButton;
    TextView txtReviewName;
    RecyclerView recyclerView;
    DatabaseReference tradeReviewRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_review);

        tradeID = getIntent().getStringExtra("tradeId");
        tradeName = getIntent().getStringExtra("tradeName");
        txtReviewName = findViewById(R.id.txtReviews);
        tradeReviewRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Reviews");

        closeButton = (Button) findViewById(R.id.btnClose);

        recyclerView = findViewById(R.id.recycler_product_review);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtReviewName.setText(tradeName + " Reviews");

        closeButton.setOnClickListener(new View.OnClickListener() {
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
        FirebaseRecyclerOptions<ProductReviews> options =
                new FirebaseRecyclerOptions.Builder<ProductReviews>()
                        .setQuery(tradeReviewRef, ProductReviews.class).build();

        FirebaseRecyclerAdapter<ProductReviews, ProductReviewViewHolder> adapter =
                new FirebaseRecyclerAdapter<ProductReviews, ProductReviewViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductReviewViewHolder productReviewViewHolder, int i, @NonNull ProductReviews productReviews) {
                        productReviewViewHolder.txtReview.setText(productReviews.getReview());
                    }

                    @NonNull
                    @Override
                    public ProductReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_review_layout, parent, false);
                        ProductReviewViewHolder holder = new ProductReviewViewHolder(view);
                        return holder;
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        //END OF YOUTUBE CODE
    }
}