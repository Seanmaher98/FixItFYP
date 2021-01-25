package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.R;

public class ServiceViewHolder extends RecyclerView.ViewHolder {

    public TextView mTitle, mDesc;

    public ServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        this.mTitle = itemView.findViewById(R.id.textViewService);
        this.mDesc = itemView.findViewById(R.id.textViewServiceDescription);
    }
}
  /**  public void ItemClickListener(ItemClickListener listener){
        this.listener =listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
**/