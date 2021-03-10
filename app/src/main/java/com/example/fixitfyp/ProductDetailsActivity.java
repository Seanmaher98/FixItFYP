package com.example.fixitfyp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Dialogs.ExampleDialog;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class ProductDetailsActivity extends AppCompatActivity {
    //This activity was created based on the below video, the video shows how to create a product details page for an e-commerce app
    //How to make an e-commerce app tutorial 21 - Coding Cafe, https://youtu.be/enyPmr6XhlQ
    private Button bookButton;
    private Button closeButton, dateButton;
    private Button viewPriceButton, viewReviewsButton;
    private ImageView tradeProfileImage;
    private TextView name, job, email, phone, userName;
    private DatePickerDialog datePickerDialog;
    ProgressBar loading;
    //private EditText date_in, time_in;
    private String tradeID = "";
    String uid;
    FirebaseUser user;
    DatabaseReference imageRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        tradeID = getIntent().getStringExtra("tradeId");

        bookButton = (Button) findViewById(R.id.btnBook);
        closeButton = (Button) findViewById(R.id.btnClose);
        viewPriceButton = (Button) findViewById(R.id.Prices);
        viewReviewsButton = (Button) findViewById(R.id.viewReviews);

        dateButton = (Button) findViewById(R.id.datePickerButton);

        name = (TextView) findViewById(R.id.product_name_details);
        email = (TextView) findViewById(R.id.product_details_description);
        job = (TextView) findViewById(R.id.product_details_job);
        phone = (TextView) findViewById(R.id.product_details_phone);
        userName = (TextView) findViewById(R.id.loggedinUser);
        tradeProfileImage = (ImageView) findViewById(R.id.trade_profile_image);
        loading = findViewById(R.id.loadingBar);

        imageRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Images");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //viewPriceButton = (Button) findViewById(R.id.Prices);
        getTradeDetails(tradeID);
        getTradeImage();
        initDatePicker();

        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Users users = snapshot.getValue(Users.class);
                            //This sets the textviews to the data pulled from firebase
                            userName.setText(users.getUserName());
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getTradeImage() {
        imageRef.child("imageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                Picasso.get().load(link).into(tradeProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //The date picker was created with the help of Youtube Tutorial - https://youtu.be/qCoidM98zNk
    //Pop Up Date Picker Android Studio Tutorial
    //START OF YOUTUBE CODE
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
        //END OF YOUTUBE CODE
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
                    //Picasso.get().load(products.getTradeImage()).into(tradeProfileImage);
                    viewReviewsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProductDetailsActivity.this, ProductsReviewActivity.class);
                            intent.putExtra("tradeId", products.getTradeId());
                            intent.putExtra("tradeName", products.getTradeName());
                            startActivity(intent);
                        }
                    });

                   viewPriceButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ProductDetailsActivity.this, ProductsPriceActivity.class);
                            intent.putExtra("tradeId", products.getTradeId());
                            intent.putExtra("tradeName", products.getTradeName());
                            startActivity(intent);
                        }
                    });


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
                if (dateButton.getText().toString().equals("")){
                    Toast.makeText(ProductDetailsActivity.this, "You must select a date", Toast.LENGTH_LONG).show();
                }
                else {
                    addingToUserTradeBookingList();
                    openDialog();
                }
            }
        });
    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "Booking Dialog");
    }

    //This code adds the booking to the database
        //It uses 2 hashmaps, 1 to send details to Trades and 1 to send to Users
        private void addingToUserTradeBookingList() {
            loading.setVisibility(View.VISIBLE);
            String saveCurrentTime, saveCurrentDate;

            Calendar callForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd, MM, yyyy");
            saveCurrentDate = currentDate.format(callForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(callForDate.getTime());

            String userId = uid;

            //Creates a random key for the bookingId
            UUID bookingId = UUID.randomUUID();
            //Trades in DB
            DatabaseReference tradeBookRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Bookings");

            HashMap<String, Object> tradeMap = new HashMap<>();
            tradeMap.put("bookingId", bookingId.toString());
            tradeMap.put("userId", userId);
            tradeMap.put("userEmail", user.getEmail());
            tradeMap.put("userName", userName.getText().toString());
            tradeMap.put("date", saveCurrentDate);
            tradeMap.put("time", saveCurrentTime);
            tradeMap.put("BookingDate", dateButton.getText().toString());

            tradeBookRef.child(String.valueOf(bookingId)).updateChildren(tradeMap);

            //Users in DB
            DatabaseReference userBookRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Bookings");

            HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("bookingId", bookingId.toString());
            cartMap.put("tradeId", tradeID);
            cartMap.put("tradeName", name.getText().toString());
            cartMap.put("userName", userName.getText().toString());
            cartMap.put("date", saveCurrentDate);
            cartMap.put("time", saveCurrentTime);
            cartMap.put("BookingDate", dateButton.getText().toString());


            userBookRef.child(String.valueOf(bookingId)).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        loading.setVisibility(View.INVISIBLE);


                    } else {
                        //An error handling message as our function is working properly
                        Toast.makeText(ProductDetailsActivity.this, "Details not saved", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.INVISIBLE);
                    }
                }

            });
        }
        //Calls what happens when the button for select date is clicked on
        public void openDatePicker(View view) {
            datePickerDialog.show();
            bookButton.setEnabled(true);
        }
    }