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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.Images;
import com.example.fixitfyp.Model.Photos;
import com.example.fixitfyp.ViewHolder.PhotosViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UploadJobPics extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    Button btnUpload;
    RecyclerView recyclerView;

    Uri imageUri;
    String uid;
    FirebaseUser user;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
    StorageReference reference = FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_job_pics);

        btnUpload = (Button) findViewById(R.id.btnSave);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_photos);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Photos"), RESULT_LOAD_IMAGE);
            }
        });
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
                        root.child(uid).child("Images").child("jobImage").setValue(image);

                        HashMap imagesHashMap = new HashMap();
                        imagesHashMap.put("tradeImage", image.getImageUrl());
                        root.child(uid).updateChildren(imagesHashMap);

                        showToastUpload();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            if (data.getClipData() != null) {
                Toast.makeText(UploadJobPics.this, "Selected Multiple files", Toast.LENGTH_SHORT).show();
                uploadPictures(imageUri);
            } else if (data.getData() != null) {
                Toast.makeText(UploadJobPics.this, "Selected Single file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //This recycler view is a firebase recycler view, it is used to pull the data from my database.
        //The code uses my database reference to pull data from my "Trades" database path
        FirebaseRecyclerOptions<Photos> options =
                new FirebaseRecyclerOptions.Builder<Photos>()
                        .setQuery(root, Photos.class)
                        .build();

        FirebaseRecyclerAdapter<Photos, PhotosViewHolder> adapter =
                new FirebaseRecyclerAdapter<Photos, PhotosViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PhotosViewHolder photosViewHolder, int i, @NonNull Photos photos) {
                        Picasso.get().load(photos.getJobImage()).into(photosViewHolder.imgUpload);
                    }

                    @NonNull
                    @Override
                    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        //inflates my cardview containing database data
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_item_layout, parent, false);
                        PhotosViewHolder holder = new PhotosViewHolder(view);
                        return holder;
                    }
                };
    }
}