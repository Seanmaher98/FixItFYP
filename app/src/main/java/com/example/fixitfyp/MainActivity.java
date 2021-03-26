package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
    //This activity is the splash screen that displays when the app is launched
    public static int SPLASH_SCREEN = 3000;
    ImageView HomeScreenLogo;
    CardView CardViewLogo;
    ProgressBar loadBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeScreenLogo = findViewById(R.id.logo);
        CardViewLogo = findViewById(R.id.cardviewLogo);
        loadBar = findViewById(R.id.logo_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

}
