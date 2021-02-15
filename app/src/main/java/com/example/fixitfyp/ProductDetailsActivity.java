package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    //This activity was created based on the below video, the video shows how to create a product details page for an e-commerce app
    //How to make an e-commerce app tutorial 21 - Coding Cafe, https://youtu.be/enyPmr6XhlQ
    private Button bookButton;
    private Button closeButton;
    private Button viewPriceButton;
    private TextView name, job, email, phone;
    private EditText date_in, time_in;
    private String tradeID = "";
    String uid;
    FirebaseUser user;


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
        date_in = (EditText) findViewById(R.id.product_details_date);
        time_in = (EditText) findViewById(R.id.product_details_time);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //viewPriceButton = (Button) findViewById(R.id.Prices);


        getTradeDetails(tradeID);
    }


    //How to make an e-commerce app tutorial 21 - Coding Cafe, https://youtu.be/enyPmr6XhlQ
    //START OF YOUTUBE CODE
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
        //END OF YOUTUBE CODE

            closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
            bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToUserBookingList();
                addingToTradeBookingList();
            }
        });
            //Commented out as not working
           /**viewPriceButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(getApplicationContext(), ProductsPriceActivity.class));
               }
           });**/
    }

    private void addingToTradeBookingList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd, MM, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        String userId = uid;

        DatabaseReference tradeBookRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Bookings");

        HashMap<String, Object> tradeMap = new HashMap<>();
        tradeMap.put("userId", userId);
        tradeMap.put("userName", user.getEmail());
        tradeMap.put("date", saveCurrentDate);
        tradeMap.put("time", saveCurrentTime);

        tradeBookRef.child(userId).updateChildren(tradeMap);
    }

    private void addingToUserBookingList() {
        String saveCurrentTime, saveCurrentDate;

        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd, MM, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());

        String userId = uid;

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Bookings");

        HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("tradeId", tradeID);
        cartMap.put("tradeName", name.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);

        cartListRef.child(tradeID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Booking has been saved", Toast.LENGTH_SHORT).show();


                } else {
                    //An error handling message as our function is working properly
                    Toast.makeText(ProductDetailsActivity.this, "Details not saved", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

}