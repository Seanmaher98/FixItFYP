package com.example.fixitfyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHome extends AppCompatActivity {
    TextView nameText;
    TextView emailText;
    ImageView nameImage;
    ImageView emailImage;

    Button btnLogOut;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        nameText =findViewById(R.id.textViewUserName);
        emailText =findViewById(R.id.textViewUserEmail);
        btnLogOut = findViewById(R.id.button_LogOut);
        nameImage = findViewById(R.id.imageViewUserName);
        emailImage = findViewById(R.id.imageViewUserEmail);

        database.getInstance().getReference("Users")
                .child(firebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            //This sets the textviews to the data pulled from firebase
                            nameText.setText(snapshot.child("userName").getValue(String.class));
                            emailText.setText(snapshot.child("userEmail").getValue(String.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "You are logged in as a guest", Toast.LENGTH_LONG).show();

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

    }
}