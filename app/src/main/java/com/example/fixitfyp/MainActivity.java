package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //This activity is not yet in use, it is potentially going to be a splash screen
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
