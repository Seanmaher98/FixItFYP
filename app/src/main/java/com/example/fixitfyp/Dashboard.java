package com.example.fixitfyp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.fixitfyp.Model.Images;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {
    //Declare Variables
    TextView nameText, emailText, phoneText, jobText;
    ImageView nameImage, emailImage, phoneImage, jobImage;
    ImageView tradeProfileImage;
    ProgressBar progressJob, progressReview, progressPrice, progressMessage;
    CardView setPriceCard, upcomingJobsCard, messagesCard, reviewsCard;
    Uri imageUri;
    Button btnLogOut, btnUploadPhotos;

    String uid;
    FirebaseUser user;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Trades");
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        //Initiate
        nameText =findViewById(R.id.textViewName);
        emailText =findViewById(R.id.textViewEmail);
        phoneText =findViewById(R.id.textViewPhone);
        jobText =findViewById(R.id.textViewJob);

        nameImage = findViewById(R.id.imageViewName);
        emailImage = findViewById(R.id.imageViewEmail);
        phoneImage = findViewById(R.id.imageViewPhone);
        jobImage = findViewById(R.id.imageViewJob);

        btnLogOut = findViewById(R.id.button_LogOut);
        btnUploadPhotos = findViewById(R.id.button_uploadPics);

        setPriceCard = findViewById(R.id.trade_set_prices);
        upcomingJobsCard = findViewById(R.id.trade_upcoming_jobs);
        messagesCard = findViewById(R.id.trade_messages);
        reviewsCard = findViewById(R.id.trade_reviews);

        tradeProfileImage = (ImageView) findViewById(R.id.trade_profile_image);
        addExistingPhoto();

        //This code reads from our database
        //It reads from the users node of the database and pulls the relevant data to the logged in user
        FirebaseDatabase.getInstance().getReference("Trades")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            //Products products = snapshot.getValue(Products.class);
                            //This sets the textViews to the data pulled from firebase
                            nameText.setText(snapshot.child("tradeName").getValue(String.class));
                            emailText.setText(snapshot.child("tradeEmail").getValue(String.class));
                            phoneText.setText(snapshot.child("tradePhone").getValue(String.class));
                            jobText.setText(snapshot.child("tradeJob").getValue(String.class));

                        }
                        else {
                            //Used to stopped users who are not registered as trades checking the tradesman check box
                            showToastIncorrectLogin();
                            startActivity(new Intent(getApplicationContext(), UserNavigationActivity.class));
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });

            tradeProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choosePicture();
                }
            });

            setPriceCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Opens set price activity so tradesmen can edit/set prices for jobs
                    startActivity(new Intent(getApplicationContext(), TradesPricesActivity.class));
                }
            });

            upcomingJobsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), TradeUpcomingJobs.class));
                }
            });
            messagesCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Messages not possible yet so cant implement this on click
                    Toast.makeText(Dashboard.this, "You have no messages", Toast.LENGTH_LONG).show();
                }
            });
            reviewsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), TradeReviewActivity.class));
                }
            });

            btnUploadPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), UploadJobPics.class));
                }
            });

    }
    private void addExistingPhoto() {
        FirebaseDatabase.getInstance().getReference("Trades")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Images/imageUrl")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                Picasso.get().load(link).into(tradeProfileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Upload Image To Firebase Storage And add URL to Realtime Database 2020 - Coding Stuff - https://youtu.be/9-oa4OS7lUQ
    //START YOUTUBE CODE
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            tradeProfileImage.setImageURI(imageUri);
            uploadPictures(imageUri);
        }
    }

    private void uploadPictures(Uri uri) {

        StorageReference tradesRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        tradesRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                tradesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Images image = new Images(uri.toString());
                        //String imageId = root.push().getKey();
                        root.child(uid).child("Images").setValue(image);

                        HashMap imagesHashMap = new HashMap();
                        imagesHashMap.put("tradeImage", image.getImageUrl());
                        root.child(uid).updateChildren(imagesHashMap);

                        showToastUpload();
                        tradeProfileImage.setImageURI(imageUri);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToastFail();
            }
        });
    }
    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
    //END YOUTUBE CODE

    public void showToastUpload() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_message);

        toastText.setText("Upload Successful");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showToastFail() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_message);

        toastText.setText("Oops, it looks like something went wrong, please try again");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void showToastIncorrectLogin() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.toast_message);

        toastText.setText("Oops, our records show you are not a tradesman");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}