package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TradesPricesActivity extends AppCompatActivity {

    private Button btnSave;
    private Button btnClose;

    private TextView calloutPrice;
    private TextView consultationPrice;

    FirebaseAuth fAuth;
    DatabaseReference dbTradeRef;
    FirebaseDatabase fDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trades_prices);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.btnClose);
        calloutPrice = findViewById(R.id.set_callout_price);
        consultationPrice = findViewById(R.id.set_consultation_price);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPrices();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void addPrices() {
        FirebaseUser rTrade = fAuth.getCurrentUser();
        String tradeId = rTrade.getUid();

        dbTradeRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeId).child("Prices");
        HashMap<String, String> hashMapTradePrices = new HashMap<>();
        hashMapTradePrices.put("tradePrice1", calloutPrice.getText().toString());
        hashMapTradePrices.put("tradePrice2", consultationPrice.getText().toString());

        dbTradeRef.setValue(hashMapTradePrices).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(TradesPricesActivity.this, "Prices have been added", Toast.LENGTH_SHORT).show();
                } else {
                    //An error handling message as our function is working properly
                    Toast.makeText(TradesPricesActivity.this, "Prices not saved", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}

