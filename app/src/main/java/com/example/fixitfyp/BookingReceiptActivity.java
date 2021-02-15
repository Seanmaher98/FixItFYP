package com.example.fixitfyp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BookingReceiptActivity extends AppCompatActivity {

    TextView name, date, time;
    private String tradeID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_receipt);

        name = findViewById(R.id.booking_name_details);
        date = findViewById(R.id.booking_date);
        time = findViewById(R.id.booking_time);
        tradeID = getIntent().getStringExtra("tradeId");

    }

}