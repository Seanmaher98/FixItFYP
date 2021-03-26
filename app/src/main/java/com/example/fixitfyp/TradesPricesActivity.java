package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Dialogs.PricesDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TradesPricesActivity extends AppCompatActivity {

    Button btnSave;
    Button btnClose;

    TextView callOutPrice;
    TextView consultationPrice;
    TextView weekendPrice;
    TextView urgentPrice;

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
        callOutPrice = findViewById(R.id.set_callout_price);
        consultationPrice = findViewById(R.id.set_consultation_price);
        weekendPrice = findViewById(R.id.set_weekendCall);
        urgentPrice = findViewById(R.id.set_sameday_price);


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
    //This code has been re-used from my Trade and user sign up fragments
    //It saves the prices set by the logged in tradesman to the database
    private void addPrices() {
        FirebaseUser rTrade = fAuth.getCurrentUser();
        String tradeId = rTrade.getUid();

        dbTradeRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeId).child("Prices");
        HashMap<String, String> hashMapTradePrices = new HashMap<>();
        hashMapTradePrices.put("tradePrice1", callOutPrice.getText().toString());
        hashMapTradePrices.put("tradePrice2", consultationPrice.getText().toString());
        hashMapTradePrices.put("tradePrice3", weekendPrice.getText().toString());
        hashMapTradePrices.put("tradePrice4", urgentPrice.getText().toString());

        dbTradeRef.setValue(hashMapTradePrices).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    openDialog();
                } else {
                    //An error handling message as our function is working properly
                    Toast.makeText(TradesPricesActivity.this, "Prices not saved", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void openDialog() {
        PricesDialog pricesDialog = new PricesDialog();
        pricesDialog.show(getSupportFragmentManager(), "Prices Dialog");
    }
}

