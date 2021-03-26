package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Interface.ItemClickListener;
import com.example.fixitfyp.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Similar to ProductViewHolder except is used in displaying bookings to tradesman in Upcoming Jobs section
    //The view holders allow the data to be repeated in the cardview/recyclerview
    public TextView txtUserId, txtUserName ,txtUserEmail, txtUserBookingDate;
    public Button btnReview, btnMessageClient, btnAccept, btnReject;
    public ItemClickListener listener;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        txtUserId = (TextView) itemView.findViewById(R.id.user_id);
        txtUserName = (TextView) itemView.findViewById(R.id.user_name);
        txtUserEmail = (TextView) itemView.findViewById(R.id.user_email);
        txtUserBookingDate = (TextView) itemView.findViewById(R.id.user_BookingDate);
        btnReview = (Button) itemView.findViewById(R.id.btnReviewClient);
        btnMessageClient = (Button) itemView.findViewById(R.id.btnMessageClient);
        btnAccept = (Button) itemView.findViewById(R.id.btnAccept);
        btnReject = (Button) itemView.findViewById(R.id.btnReject);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {listener.onClick(view, getAdapterPosition(),false);

    }
}
