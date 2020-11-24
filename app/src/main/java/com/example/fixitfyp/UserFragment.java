package com.example.fixitfyp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFragment extends Fragment {
    //Line 21 to 85 are based off a youtube tutorial - https://youtu.be/EM2x33g4syY
    //Note the code has been modified, variables have been changed and added by me to suit my project
    EditText editUserFirstName;
    EditText editUserLastName;
    EditText editUserEmail;
    EditText editUserAddressLine1;
    EditText editUserAddressLine2;
    EditText editUserAddressLineTown;
    Spinner UserspinnerCounty;
    Button buttonAddUser;

    DatabaseReference databaseUsers;
    Users user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //Assign variables
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        editUserFirstName = (EditText) view.findViewById(R.id.editUserFirstName);
        editUserLastName = (EditText) view.findViewById(R.id.editUserLastName);
        editUserEmail = (EditText) view.findViewById(R.id.editUserEmail);
        editUserAddressLine1 = (EditText) view.findViewById(R.id.editUserAddressLine1);
        editUserAddressLine2 = (EditText) view.findViewById(R.id.editUserAddressLine2);
        editUserAddressLineTown = (EditText) view.findViewById(R.id.editUserAddressLineTown);
        UserspinnerCounty = (Spinner) view.findViewById(R.id.UserspinnerCounty);
        buttonAddUser = (Button) view.findViewById(R.id.buttonAddUser);
        user = new Users();


        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling method to add to database
                addUser();
            }
        });
        return view;
    }

    private void addUser(){
        //Getters and Setters Youtube Tutorial
        //Gets the text entered into our textbox

        user.setUserFirstName(editUserFirstName.getText().toString());
        user.setUserLastName(editUserLastName.getText().toString());
        user.setUserEmail(editUserEmail.getText().toString());
        user.setUserAddressLine1(editUserAddressLine1.getText().toString());
        user.setUserAddressLine2(editUserAddressLine2.getText().toString());
        user.setUserAddressLineTown(editUserAddressLineTown.getText().toString());
        user.setUserCounty(UserspinnerCounty.getSelectedItem().toString());


        //Sends the text inputted into the fields to the database
        FirebaseDatabase.getInstance().getReference("Users").push().setValue(user);

        Toast.makeText(getActivity(), "Congratulations! You are now Registered", Toast.LENGTH_LONG).show();
    }
    //END OF CODE FROM YOUTUBE

}