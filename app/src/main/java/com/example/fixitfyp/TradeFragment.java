package com.example.fixitfyp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TradeFragment extends Fragment {
    //Line 21 TO 102 are based off a youtube tutorial - https://youtu.be/EM2x33g4syY
    //Note the code has been modified, variables have been changed and added by me to suit my project
    //Declaring my variables
    EditText editFirstName;
    EditText editLastName;
    EditText editEmail;
    EditText editAddressLine1;
    EditText editAddressLine2;
    EditText editAddressLineTown;
    Spinner spinnerCounty;
    Spinner spinnerJobs;
    Button buttonAdd;

    DatabaseReference databaseTrades;
    Trades trade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trade, container, false);
        //Assign variables
        databaseTrades = FirebaseDatabase.getInstance().getReference("Trades");

        editFirstName = (EditText) view.findViewById(R.id.editFirstName);
        editLastName = (EditText) view.findViewById(R.id.editLastName);
        editEmail = (EditText) view.findViewById(R.id.editEmail);
        editAddressLine1 = (EditText) view.findViewById(R.id.editAddressLine1);
        editAddressLine2 = (EditText) view.findViewById(R.id.editAddressLine2);
        editAddressLineTown = (EditText) view.findViewById(R.id.editAddressLineTown);
        spinnerCounty = (Spinner) view.findViewById(R.id.spinnerCounty);
        spinnerJobs = (Spinner) view.findViewById(R.id.spinnerJobs);
        buttonAdd = (Button) view.findViewById(R.id.buttonAddTrade);
        trade = new Trades();

        //Code that checks if there is text input into text fields
        editFirstName.addTextChangedListener(tradeTextWatcher);
        editLastName.addTextChangedListener(tradeTextWatcher);
        editEmail.addTextChangedListener(tradeTextWatcher);
        editAddressLine1.addTextChangedListener(tradeTextWatcher);
        editAddressLine2.addTextChangedListener(tradeTextWatcher);
        editAddressLineTown.addTextChangedListener(tradeTextWatcher);




//Click Listener allows the addTrade function to be called when button is clicked
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling method to add to database
                addTrade();
            }

        });
        return view;

    }



    private void addTrade(){
        //Getters and Setters Youtube Tutorial
        //Gets the text entered into the fields

        trade.setTradeFirstName(editFirstName.getText().toString());
        trade.setTradeLastName(editLastName.getText().toString());
        trade.setTradeEmail(editEmail.getText().toString());
        trade.setTradeAddressLine1(editAddressLine1.getText().toString());
        trade.setTradeAddressLine2(editAddressLine2.getText().toString());
        trade.setTradeAddressLineTown(editAddressLineTown.getText().toString());
        trade.setTradeCounty(spinnerCounty.getSelectedItem().toString());
        trade.setTradeJobs(spinnerJobs.getSelectedItem().toString());

        //Sends the text inputted into the app to the firebase database
        //Trades is the name of the folder it is stored in on firebase
        FirebaseDatabase.getInstance().getReference("Trades").push().setValue(trade);

        Toast.makeText(getActivity(), "Congratulations! You are now Registered", Toast.LENGTH_LONG).show();
    }
    //END OF CODE FROM YOUTUBE


    //Youtube code to disable button when text boxes are empty
    //https://youtu.be/Vy_4sZ6JVHM, Disable Button When EditText Is Empty (TextWatcher), Coding In flow
    private TextWatcher tradeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String firstNameInput = editFirstName.getText().toString().trim();
            String lastNameInput = editLastName.getText().toString().trim();
            String emailInput = editEmail.getText().toString().trim();
            String addressInput = editAddressLine1.getText().toString().trim();
            String address2Input = editAddressLine2.getText().toString().trim();
            String addressTownInput = editAddressLineTown.getText().toString().trim();
            //Calls method below to see if email address is valid
            validateEmail();
            //Enables button when fields are populated
            buttonAdd.setEnabled(!firstNameInput.isEmpty() && !lastNameInput.isEmpty() &&
            !emailInput.isEmpty() && validateEmail() && !addressInput.isEmpty() && !address2Input.isEmpty()
                    && !addressTownInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    //Youtube - Validate Email & Password with Regular Expression - Android Studio Tutorial (https://youtu.be/cnD_7qFeZcY)
    //making email be valid email
    private boolean validateEmail() {
        String emailInput = editEmail.getText().toString().trim();

        if (emailInput.isEmpty()){
            editEmail.setError("This Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editEmail.setError("This email address is not valid");
            return false;
        }else {
            editEmail.setError(null);
            return true;
        }
    }
    //END OF CODE YOUTUBE


}
