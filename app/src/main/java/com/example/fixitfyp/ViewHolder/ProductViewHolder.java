package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Interface.ItemClickListener;
import com.example.fixitfyp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Used as a placeholder for the data that will be pulled from firebase
    //Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe, https://youtu.be/745ElNRjJew
    //The view holders allow the data to be repeated in the cardview/recyclerview
    public TextView txtProductName, txtProductDescription;
    public ImageView imgProduct;

    public ItemClickListener listener;


    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        imgProduct = (ImageView) itemView.findViewById(R.id.user_profile_image_items);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(),false);
    }
}
