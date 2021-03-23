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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.fixitfyp.Dialogs.ErrorDialog;
import com.example.fixitfyp.Model.Images;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Objects;

public class MyAccount extends AppCompatActivity {

    AppBarConfiguration mAppBarConfiguration;
    TextView viewPhone, viewEmail;
    ImageView viewImage;
    TextView userName;
    Button btnClose;
    String uid;
    Uri imageUri;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        viewPhone = (TextView) findViewById(R.id.Phone);
        viewEmail = (TextView) findViewById(R.id.Email);
        viewImage = (ImageView) findViewById(R.id.imgUser);
        userName = (TextView) findViewById(R.id.userNameBig);
        btnClose = (Button) findViewById(R.id.btnClose);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        addExistingPhoto();

        //Adapted from the Dashboard Activity this code displays the logged in users username
        FirebaseDatabase.getInstance().getReference("Users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Users users = snapshot.getValue(Users.class);
                            //This sets the textviews to the data pulled from firebase
                            userName.setText(users.getUserName());
                            viewPhone.setText(users.getUserPhone());
                            viewEmail.setText(users.getUserEmail());
                        } else {
                            openDialog();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
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
        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.show(getSupportFragmentManager(), "Error Dialog");
    }

    private void addExistingPhoto() {
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Images/imageUrl")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link = snapshot.getValue(String.class);
                        Picasso.get().load(link).into(viewImage);
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
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            viewImage.setImageURI(imageUri);
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
                        imagesHashMap.put("userImage", image.getImageUrl());
                        root.child(uid).updateChildren(imagesHashMap);

                        showToastUpload();
                        viewImage.setImageURI(imageUri);
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
}