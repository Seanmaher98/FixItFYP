package com.example.fixitfyp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button btnLoginUser;
    CheckBox tradeCheck;
    FirebaseAuth fAuth;
    TextView tvSignUp;
    ProgressBar loading;
    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.userEmailLogin);
        mPassword = findViewById(R.id.userPasswordLogin);
        btnLoginUser = findViewById(R.id.buttonUserLogin);
        loading = findViewById(R.id.loadingBar);
        tradeCheck = findViewById(R.id.checkBoxTrade);
        tvSignUp = findViewById(R.id.textSignUp);
        fAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        //This text view allows users who are not registered to navigate to the sign-up class
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.setVisibility(View.VISIBLE);
                Intent intSignUp = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intSignUp);
            }
        });

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //I had to insert this if statement as my app kept force closing when the button was pressed and the fields were empty
                AllowAccess(mEmail.toString(), mPassword.toString());
            }

            //Youtube - Validate Email & Password with Regular Expression - Android Studio Tutorial (https://youtu.be/cnD_7qFeZcY)
            //making email be valid email address
            private boolean validateEmail() {
                String emailInput = mEmail.getText().toString().trim();
                String passwordInput = mPassword.getText().toString().trim();

                if (emailInput.isEmpty() && passwordInput.isEmpty()) {
                    showToast();
                    return false;
                } else if (passwordInput.isEmpty()) {
                    showToast();
                    return false;
                } else if (emailInput.isEmpty()) {
                    showToast();
                    return false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    mEmail.setError("This email address is not valid");
                    return false;
                } else {
                    mEmail.setError(null);
                    loading.setVisibility(View.VISIBLE);
                    return true;
                }
            }
            //END OF CODE YOUTUBE

            private void AllowAccess(final String email1, final String password1) {
                validateEmail();
                if (!validateEmail())
                    validateEmail();
                else {
                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    //Authentication process, I have used "if" statements to verify users, the check box is used to differentiate users and tradesmen
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() && tradeCheck.isChecked()) {
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();
                            }
                            else if (task.isSuccessful() && !tradeCheck.isChecked())
                                startActivity(new Intent(getApplicationContext(), UserNavigationActivity.class));
                            else if (!validateEmail())
                                showToast();
                            else {
                                showToast();
                                mPassword.getText().clear();
                            }
                        }
                    });

                }
            }
        });

    }
    public void showToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_message);

        toastText.setText("Hey, Looks like something went wrong, please check details and try again!");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        loading.setVisibility(View.INVISIBLE);

    }

}

