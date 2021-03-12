package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Interface.ItemClickListener;
import com.example.fixitfyp.R;

public class ProductReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Similar to ProductViewHolder except is used in displaying bookings to tradesman in Upcoming Jobs section
    //The view holders allow the data to be repeated in the cardview/recyclerview
    public TextView txtReview, txtReviewId;
    public ItemClickListener listener;

    public ProductReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        txtReview = (TextView) itemView.findViewById(R.id.product_review);
        txtReviewId = (TextView) itemView.findViewById(R.id.product_reviewHeading);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {listener.onClick(view, getAdapterPosition(),false);

    }
}
