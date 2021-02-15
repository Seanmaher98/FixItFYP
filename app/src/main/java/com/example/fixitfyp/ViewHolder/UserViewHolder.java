package com.example.fixitfyp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Interface.ItemClickListener;
import com.example.fixitfyp.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtUserName;
    public ItemClickListener listener;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        txtUserName = (TextView) itemView.findViewById(R.id.user_name);
    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {listener.onClick(view, getAdapterPosition(),false);

    }
}
