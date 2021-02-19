package com.example.fixitfyp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductsPriceActivity extends AppCompatActivity {
    //ToDo Prices still not displaying (User Story 5)
    private TextView calloutPrice, consultationPrice;
    private String tradeID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_price);

        calloutPrice = (TextView) findViewById(R.id.calloutprice);
        consultationPrice = (TextView) findViewById(R.id.phone_consultation_price);
        tradeID = getIntent().getStringExtra("tradeId");

        getTradePrice(tradeID);

    }
    private void getTradePrice(String tradeID) {
        DatabaseReference tradeRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Prices");
        //This part checks what tradeId is selected, it then uses a data snapshot to get the corresponding data from firebase
        tradeRef.child(tradeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products = snapshot.getValue(Products.class);
                    //sets the values of my text views to selected items data
                    calloutPrice.setText(products.getTradePrice1());
                    consultationPrice.setText(products.getTradePrice2());

                } else {
                    Toast.makeText(ProductsPriceActivity.this, "Prices Not Set by Tradesman", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}