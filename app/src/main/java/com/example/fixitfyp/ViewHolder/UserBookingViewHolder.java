package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Interface.ItemClickListener;
import com.example.fixitfyp.R;

public class UserBookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //Similar to ProductViewHolder except is used in displaying bookings to user
    //The view holders allow the data to be repeated in the cardview/recyclerview
    public TextView txtTradeName, txtDate, txtBookingId, txtTradeId;
    public Button btnCancel;
    public ItemClickListener listener;

    public UserBookingViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTradeName = (TextView) itemView.findViewById(R.id.trade_name);
        txtDate = (TextView) itemView.findViewById(R.id.trade_date);
        txtBookingId = (TextView) itemView.findViewById(R.id.bookingId);
        txtTradeId = (TextView) itemView.findViewById(R.id.tradeId);

        btnCancel = (Button) itemView.findViewById(R.id.btnDeleteBooking);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {listener.onClick(view, getAdapterPosition(),false);

    }
}
