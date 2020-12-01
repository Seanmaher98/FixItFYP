package com.example.fixitfyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class UserFragment extends Fragment {
    //ITERATION 1 Line 21 to 93 are based off a youtube tutorial - https://youtu.be/EM2x33g4syY
    //ITERATION 2 This class has been modified in order to allow users to be authenticated when signing up
    //Create Login And Registration Screen In Android Using Firebase - https://youtu.be/V0ZrnL-i77Q
    //Note the code has been modified, variables have been changed and added by me to suit my project
    //Declaring my variables
    EditText userName;
    EditText userEmail;
    EditText userPassword;
    EditText userPhone;
    Button buttonAddUser;
    TextView tvSignIn;

    FirebaseAuth fAuth;
    DatabaseReference dbRef;
    FirebaseDatabase fDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //Assign variables
        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();

        userName = (EditText) view.findViewById(R.id.editUserName);
        userEmail = (EditText) view.findViewById(R.id.editUserEmail);
        userPassword = (EditText) view.findViewById(R.id.editUserPassword);
        userPhone = (EditText) view.findViewById(R.id.editPhoneNumber);
        buttonAddUser = (Button) view.findViewById(R.id.buttonAddUser);
        tvSignIn =(TextView) view.findViewById(R.id.textAlreadyMember);


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignIn = new Intent(getActivity(), LoginActivity.class);
                startActivity(intSignIn);
            }
        });

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
                //This code has been taken from YouTube
                //Create Login And Registration Screen In Android Using Firebase - https://youtu.be/V0ZrnL-i77Q
                String email = userEmail.getText().toString();
                String pwd = userPassword.getText().toString();
                if(email.isEmpty()){
                    userEmail.setError("Please enter email");
                    userEmail.requestFocus();
                }
                else if(pwd.isEmpty()){
                    userPassword.setError("Please enter password");
                    userPassword.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(getContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    fAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                addUser();
                            }
                            else {
                                Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void addUser(){
        FirebaseUser rUser = fAuth.getCurrentUser();
        String userId = rUser.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", userId);
        hashMap.put("userName", userName.getText().toString());
        hashMap.put("userEmail", userEmail.getText().toString());
        hashMap.put("userPhone", userPhone.getText().toString());
        dbRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Congratulations you are now a member", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //End of YouTube Create Login And Registration Screen In Android Using Firebase - https://youtu.be/V0ZrnL-i77Q
    }



    //Youtube - Validate Email & Password with Regular Expression - Android Studio Tutorial (https://youtu.be/cnD_7qFeZcY)
    //making email be valid email
    private boolean validateEmail() {
        String emailInput = userEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            userEmail.setError("This Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            userEmail.setError("This email address is not valid");
            return false;
        } else {
            userEmail.setError(null);
            return true;
        }
    }
    //END OF CODE YOUTUBE
}