package com.example.fixitfyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button btnLoginUser;
    FirebaseAuth fAuth;
    TextView tvSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.userEmailLogin);
        mPassword = findViewById(R.id.userPasswordLogin);
        btnLoginUser = findViewById(R.id.buttonUserLogin);
        tvSignUp = findViewById(R.id.textSignUp);
        fAuth = FirebaseAuth.getInstance();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intSignUp);
            }
        });

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                validateEmail();
                //Authenticate
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            finish();
                        } else if (validateEmail() == false)
                        Toast.makeText(LoginActivity.this, "Email Not Valid", Toast.LENGTH_LONG).show();
                        else{
                            Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                            mPassword.getText().clear();
                        }
                    }
                });
            }
        });

    }

    //Youtube - Validate Email & Password with Regular Expression - Android Studio Tutorial (https://youtu.be/cnD_7qFeZcY)
    //making email be valid email
    private boolean validateEmail() {
        String emailInput = mEmail.getText().toString().trim();
        String passwordInput = mPassword.getText().toString().trim();

        if (emailInput.isEmpty()) {
            mEmail.setError("This Field can't be empty");
            return false;
        } else if (passwordInput.isEmpty()) {
            mPassword.setError("This password is incorrect");
            return false;
        } else if (emailInput.isEmpty() && passwordInput.isEmpty()) {
            mPassword.setError("Fields cannot be blank");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEmail.setError("This email address is not valid");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }
    //END OF CODE YOUTUBE
}