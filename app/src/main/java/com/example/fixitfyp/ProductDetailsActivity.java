package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Model.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailsActivity extends AppCompatActivity {

    //This activity was created based on the below video, the video shows how to create a product details page for an e-commerce app
    //How to make an e-commerce app tutorial 21 - Coding Cafe, https://youtu.be/enyPmr6XhlQ
    private Button bookButton;
    private Button closeButton;
    private TextView name, job, email, phone;
    private TextView calloutPrice, consultationPrice;
    private String tradeID = "";

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tradeID = getIntent().getStringExtra("tradeId");

        bookButton = (Button) findViewById(R.id.btnBook);
        closeButton = (Button) findViewById(R.id.btnClose);
        name = (TextView) findViewById(R.id.product_name_details);
        email = (TextView) findViewById(R.id.product_details_description);
        job = (TextView) findViewById(R.id.product_details_job);
        phone = (TextView) findViewById(R.id.product_details_phone);

        calloutPrice = (TextView) findViewById(R.id.calloutprice);
        consultationPrice = (TextView) findViewById(R.id.phone_consultation_price);

        getTradeDetails(tradeID);
    }

    private void getTradeDetails(String tradeID) {
        DatabaseReference tradeRef = FirebaseDatabase.getInstance().getReference().child("Trades");
        //This part checks what tradeId is selected, it then uses a data snapshot to get the corresponding data from firebase
        tradeRef.child(tradeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);
                    //sets the values of my text views to selected items data
                    name.setText(products.getTradeName());
                    email.setText(products.getTradeEmail());
                    job.setText(products.getTradeJob());
                    phone.setText(products.getTradePhone());


                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Details not available", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

            closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}