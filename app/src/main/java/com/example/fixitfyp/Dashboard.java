package com.example.fixitfyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    TextView nameText;
    TextView emailText;
    TextView phoneText;
    ImageView nameImage;
    ImageView emailImage;
    ImageView phoneImage;
    String email;
    String password;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        nameText =findViewById(R.id.textViewName);
        emailText =findViewById(R.id.textViewEmail);
        phoneText =findViewById(R.id.textViewPhone);
        nameImage = findViewById(R.id.imageViewName);
        emailImage = findViewById(R.id.imageViewEmail);
        phoneImage = findViewById(R.id.imageViewPhone);
    //This code reads from our database
        //It reads from our table users and pulls the relevant data to the logged in user
        database.getInstance().getReference("Users")
                .child(firebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            //This sets the textviews to the data pulled from firebase
                            nameText.setText(snapshot.child("userName").getValue(String.class));
                            emailText.setText(snapshot.child("userEmail").getValue(String.class));
                            phoneText.setText(snapshot.child("userPhone").getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}