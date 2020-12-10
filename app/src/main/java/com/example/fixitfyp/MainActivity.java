package com.example.fixitfyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ImageView HomeScreenLogo, HomeScreenBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeScreenLogo = findViewById(R.id.logo);
        HomeScreenBackground = findViewById(R.id.loadimage);
    //makes the items slide updwards/downwards on home screen
        HomeScreenLogo.animate().translationY(-1600).setDuration(2000).setStartDelay(10000);
        HomeScreenBackground.animate().translationY(1400).setDuration(2000).setStartDelay(10000);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

}
