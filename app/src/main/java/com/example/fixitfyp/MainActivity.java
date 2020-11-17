package com.example.fixitfyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //Line 17 to 85 are based off a youtube tutorial - https://youtu.be/EM2x33g4syY
    //Note the code has been modified, variables have been changed and added by me to suit my project
    EditText editFirstName;
    EditText editLastName;
    EditText editAddressLine1;
    EditText editAddressLine2;
    EditText editAddressLineTown;
    Spinner spinnerCounty;
    Spinner spinnerGenres;
    Button buttonAdd;

    DatabaseReference databaseTrades;
    Trades trade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseTrades = FirebaseDatabase.getInstance().getReference("Trades");

        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editAddressLine1 = (EditText) findViewById(R.id.editAddressLine1);
        editAddressLine2 = (EditText) findViewById(R.id.editAddressLine2);
        editAddressLineTown = (EditText) findViewById(R.id.editAddressLineTown);
        spinnerCounty = (Spinner) findViewById(R.id.spinnerCounty);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenres);
        buttonAdd = (Button) findViewById(R.id.buttonAddTrade);
        trade = new Trades();




        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling method to add to database
                addTrade();
            }
        });
    }
    private void addTrade(){
        //Getters and Setters Youtube Tutorial
        //Gets the text entered into our textbox
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String addressLine1 = editAddressLine1.getText().toString();
        String addressLine2 = editAddressLine2.getText().toString();
        String addressLineTown = editAddressLineTown.getText().toString();
        String county = spinnerCounty.getSelectedItem().toString();
        String genre = spinnerGenres.getSelectedItem().toString();

       trade.setTradeFirstName(editFirstName.getText().toString());
       trade.setTradeLastName(editLastName.getText().toString());
       trade.setTradeAddressLine1(editAddressLine1.getText().toString());
       trade.setTradeAddressLine2(editAddressLine2.getText().toString());
       trade.setTradeAddressLineTown(editAddressLineTown.getText().toString());
       trade.setTradeCounty(spinnerCounty.getSelectedItem().toString());
       trade.setTradeGenre(spinnerGenres.getSelectedItem().toString());

       //Sends the text inputted into the app to the database
       FirebaseDatabase.getInstance().getReference("Trades").push().setValue(trade);

       Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();

    }
    //END OF CODE FROM YOUTUBE
}