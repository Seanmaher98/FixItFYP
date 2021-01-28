package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button btnLoginUser;
    CheckBox tradeCheck;
    FirebaseAuth fAuth;
    TextView tvSignUp;
    TextView tvHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.userEmailLogin);
        mPassword = findViewById(R.id.userPasswordLogin);
        btnLoginUser = findViewById(R.id.buttonUserLogin);
        tradeCheck = findViewById(R.id.checkBoxTrade);
        tvSignUp = findViewById(R.id.textSignUp);
        tvHome = findViewById(R.id.textHome);
        fAuth = FirebaseAuth.getInstance();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intSignUp);
            }
        });

        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intHome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intHome);
            }
        });

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //I had to insert this if statement as my app kept when the button was pressed an the fields were empty
                validateEmail();
                if (!validateEmail())
                    validateEmail();
                else{
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                //Authenticate
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && tradeCheck.isChecked()) {
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            finish();
                        } else if (task.isSuccessful() && !tradeCheck.isChecked())
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        else if (!validateEmail())
                        Toast.makeText(LoginActivity.this, "Email Not Valid", Toast.LENGTH_LONG).show();
                        else{
                            Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                            mPassword.getText().clear();
                        }
                    }
                });
            }
            }
        });

    }

    //Youtube - Validate Email & Password with Regular Expression - Android Studio Tutorial (https://youtu.be/cnD_7qFeZcY)
    //making email be valid email
    private boolean validateEmail() {
        String emailInput = mEmail.getText().toString().trim();
        String passwordInput = mPassword.getText().toString().trim();

        if (emailInput.isEmpty() && passwordInput.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (passwordInput.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if(emailInput.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email cannot be empty", Toast.LENGTH_LONG).show();
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
