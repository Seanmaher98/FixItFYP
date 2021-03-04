package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Model.Prices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductsPriceActivity extends AppCompatActivity {
    //ToDo Prices still not displaying (User Story 5)
    private TextView calloutPrice, consultationPrice, weekendPrice, urgentPrice, tradePriceName;
    private String tradeID = "";
    private String tradeName = "";
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_price);

        tradeID = getIntent().getStringExtra("tradeId");
        tradeName = getIntent().getStringExtra("tradeName");
        tradePriceName = (TextView) findViewById(R.id.tradePriceName);
        calloutPrice = (TextView) findViewById(R.id.calloutprice);
        consultationPrice = (TextView) findViewById(R.id.phone_consultation_price);
        weekendPrice = (TextView) findViewById(R.id.weekend_price);
        urgentPrice = (TextView) findViewById(R.id.urgent_job_price);
        closeButton = (Button) findViewById(R.id.btnClose);


        tradePriceName.setText(tradeName +" prices:");
        getTradePrice(tradeID);

    }
    private void getTradePrice(String tradeID) {
        DatabaseReference tradePriceRef = FirebaseDatabase.getInstance().getReference("Trades");
        //This part checks what tradeId is selected, it then uses a data snapshot to get the corresponding data from firebase
        tradePriceRef.child(tradeID).child("Prices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Prices prices = snapshot.getValue(Prices.class);
                    //sets the values of my text views to selected items data
                    calloutPrice.setText("€" + prices.getTradePrice1());
                    consultationPrice.setText("€" + prices.getTradePrice2());
                    weekendPrice.setText("€" + prices.getTradePrice3());
                    urgentPrice.setText("€" + prices.getTradePrice4());

                } else {
                    Toast.makeText(ProductsPriceActivity.this, "Prices Not Set by Tradesman", Toast.LENGTH_LONG).show();
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