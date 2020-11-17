package com.example.fixitfyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    //Line 17 to 85 are based off a youtube tutorial - https://youtu.be/EM2x33g4syY
    //Note the code has been modified, variables have been changed and added by me to suit my project
    EditText editFirstName;
    EditText editLastName;
    EditText editAddressLine1;
    EditText editAddressLine2;
    EditText editAddressLineTown;
    Spinner spinnerCounty;
    Spinner spinnerJobs;
    Button buttonAdd;

    DatabaseReference databaseTrades;
    Trades trade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseTrades = FirebaseDatabase.getInstance().getReference("Trades");

        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editAddressLine1 = (EditText) findViewById(R.id.editAddressLine1);
        editAddressLine2 = (EditText) findViewById(R.id.editAddressLine2);
        editAddressLineTown = (EditText) findViewById(R.id.editAddressLineTown);
        spinnerCounty = (Spinner) findViewById(R.id.spinnerCounty);
        spinnerJobs = (Spinner) findViewById(R.id.spinnerJobs);
        buttonAdd = (Button) findViewById(R.id.buttonAddTrade);
        trade = new Trades();

        //looks at function signuptextwatcher to make sure fields arent blank
        editFirstName.addTextChangedListener(signupTextWatcher);
        editLastName.addTextChangedListener(signupTextWatcher);
        editAddressLine1.addTextChangedListener(signupTextWatcher);
        editAddressLineTown.addTextChangedListener(signupTextWatcher);




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
        String jobs = spinnerJobs.getSelectedItem().toString();


        trade.setTradeFirstName(editFirstName.getText().toString());
        trade.setTradeLastName(editLastName.getText().toString());
        trade.setTradeAddressLine1(editAddressLine1.getText().toString());
        trade.setTradeAddressLine2(editAddressLine2.getText().toString());
        trade.setTradeAddressLineTown(editAddressLineTown.getText().toString());
        trade.setTradeCounty(spinnerCounty.getSelectedItem().toString());
        trade.setTradeJobs(spinnerJobs.getSelectedItem().toString());


        //Sends the text inputted into the app to the database
        FirebaseDatabase.getInstance().getReference("Trades").push().setValue(trade);

        Toast.makeText(SignUp.this, "Data Inserted", Toast.LENGTH_LONG).show();

    }
    //END OF CODE FROM YOUTUBE

    //sets a textwatcher so that fields are not blank
    private TextWatcher signupTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstnameInput = editFirstName.getText().toString().trim();
            String lastnameInput = editLastName.getText().toString().trim();
            String addressInput = editAddressLine1.getText().toString().trim();
            String addresstownInput = editAddressLineTown.getText().toString().trim();

            buttonAdd.setEnabled(!firstnameInput.isEmpty() && !lastnameInput.isEmpty() && !addressInput.isEmpty() && !addresstownInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    }