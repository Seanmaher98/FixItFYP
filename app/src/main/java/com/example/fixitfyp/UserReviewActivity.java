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

public class UserReviewActivity extends AppCompatActivity {
    private EditText editTextReview;
    private TextView txtReviewName;
    private String tradeID = "";
    private String bookingID = "";
    private String tradeName = "";
    private Button btnSave, btnClose;
    String uid;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        tradeID = getIntent().getStringExtra("tradeId");
        bookingID = getIntent().getStringExtra("bookingId");
        tradeName = getIntent().getStringExtra("tradeName");
        editTextReview = (EditText) findViewById(R.id.editTextLeaveReview);
        txtReviewName = (TextView) findViewById(R.id.txtReviewName);
        btnSave = (Button) findViewById(R.id.btnSaveReview);
        btnClose = (Button) findViewById(R.id.btnClose);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        
        txtReviewName.setText("Review of " + tradeName);
        
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

    private void openDialog() {
        ReviewDialog reviewDialog = new ReviewDialog();
        reviewDialog.show(getSupportFragmentManager(), "Review Dialog");
    }

    private void saveReview() {
        String userId = uid;
        //Creates a random key for the bookingId
        UUID reviewId = UUID.randomUUID();
        DatabaseReference tradeReviewRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Reviews");

        HashMap<String, Object> tradeReviewMap = new HashMap<>();
        tradeReviewMap.put("reviewId", reviewId.toString());
        tradeReviewMap.put("review", editTextReview.getText().toString());
        tradeReviewMap.put("userId", userId);
        tradeReviewMap.put("tradeId", tradeID);
        tradeReviewMap.put("tradeName", tradeName);

        tradeReviewRef.child(String.valueOf(reviewId)).updateChildren(tradeReviewMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    openDialog();
                    editTextReview.getText().clear();

                } else {
                    //An error handling message as our function is working properly
                    Toast.makeText(UserReviewActivity.this, "Review not saved, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}