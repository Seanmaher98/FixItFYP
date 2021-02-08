package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    TextView nameText;
    TextView emailText;
    TextView phoneText;
    TextView jobText;
    ImageView nameImage;
    ImageView emailImage;
    ImageView phoneImage;
    ImageView jobImage;
    CardView setPriceCard;
    Button btnLogOut;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nameText =findViewById(R.id.textViewName);
        emailText =findViewById(R.id.textViewEmail);
        phoneText =findViewById(R.id.textViewPhone);
        jobText =findViewById(R.id.textViewJob);
        nameImage = findViewById(R.id.imageViewName);
        emailImage = findViewById(R.id.imageViewEmail);
        phoneImage = findViewById(R.id.imageViewPhone);
        jobImage = findViewById(R.id.imageViewJob);
        btnLogOut = findViewById(R.id.button_LogOut);

        setPriceCard = findViewById(R.id.trade_set_prices);

        //This code reads from our database (Lines 53-75)
        //It reads from our table users and pulls the relevant data to the logged in user
        database.getInstance().getReference("Trades")
                .child(firebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            //This sets the textviews to the data pulled from firebase
                            nameText.setText(snapshot.child("tradeName").getValue(String.class));
                            emailText.setText(snapshot.child("tradeEmail").getValue(String.class));
                            phoneText.setText(snapshot.child("tradePhone").getValue(String.class));
                            jobText.setText(snapshot.child("tradeJob").getValue(String.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You are not a registered Tradesman", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), UserNavigationActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });

            setPriceCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), TradesPricesActivity.class));
                }
            });

    }
}