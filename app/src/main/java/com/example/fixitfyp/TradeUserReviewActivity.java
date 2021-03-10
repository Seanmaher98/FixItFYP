package com.example.fixitfyp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixitfyp.Dialogs.ReviewDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class TradeUserReviewActivity extends AppCompatActivity {
    private EditText editTextReview;
    private TextView txtReviewName;
    private String userName = "";
    private String userId = "";
    private Button btnSave, btnClose;
    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_user_review);

        userName = getIntent().getStringExtra("userName");
        userId = getIntent().getStringExtra("userId");
        editTextReview = (EditText) findViewById(R.id.editTextLeaveReview);
        txtReviewName = (TextView) findViewById(R.id.txtReviewName);
        btnSave = (Button) findViewById(R.id.btnSaveReview);
        btnClose = (Button) findViewById(R.id.btnClose);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        
        txtReviewName.setText("Review of " + userName);
        
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveReview();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void saveReview() {
        String tradeId = uid;
        //Creates a random key for the bookingId
        UUID reviewId = UUID.randomUUID();
        DatabaseReference tradeUserReviewRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Reviews");

        HashMap<String, Object> tradeUserReviewMap = new HashMap<>();
        tradeUserReviewMap.put("reviewId", reviewId.toString());
        tradeUserReviewMap.put("tradeId", tradeId);
        tradeUserReviewMap.put("review", editTextReview.getText().toString());


        tradeUserReviewRef.child(String.valueOf(reviewId)).updateChildren(tradeUserReviewMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    openDialog();
                    editTextReview.getText().clear();


                } else {
                    //An error handling message as our function is working properly
                    Toast.makeText(TradeUserReviewActivity.this, "Review not saved, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void openDialog() {
        ReviewDialog reviewDialog = new ReviewDialog();
        reviewDialog.show(getSupportFragmentManager(), "Review Dialog");
    }


}