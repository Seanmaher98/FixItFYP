package com.example.fixitfyp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.Data;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {

    List<Data> fetchDataList;

    public HelperAdapter(List<Data> fetchDataList) {
        this.fetchDataList = fetchDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent,false);
        ServiceViewHolder  serviceViewHolder = new ServiceViewHolder(view);
        return serviceViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceViewHolder serviceViewHolder = (ServiceViewHolder)holder;

        Data fetchData = fetchDataList.get(position);
        serviceViewHolder.mTitle.setText(fetchData.getProdName());
        serviceViewHolder.mDesc.setText(fetchData.getProdEmail());
    }

    @Override
    public int getItemCount() {
        return fetchDataList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitle, mDesc;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            this.mTitle = itemView.findViewById(R.id.textViewService);
            this.mDesc = itemView.findViewById(R.id.textViewServiceDescription);
        }
    }
}
