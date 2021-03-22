package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Interface.ItemClickListener;
import com.example.fixitfyp.R;

public class PhotosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Similar to ProductViewHolder except is used in displaying bookings to tradesman in Upcoming Jobs section
    //The view holders allow the data to be repeated in the cardview/recyclerview
    public ImageView imgUpload;
    private ItemClickListener listener;


    public PhotosViewHolder(@NonNull View itemView) {
        super(itemView);
        imgUpload = (ImageView) itemView.findViewById(R.id.imgviewPhoto);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {listener.onClick(view, getAdapterPosition(),false);

    }
}
