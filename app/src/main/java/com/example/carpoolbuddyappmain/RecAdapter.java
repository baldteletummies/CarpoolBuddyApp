package com.example.carpoolbuddyappmain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpoolbuddyappmain.Models.Vehicle;

import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecHolder> {
    ArrayList<Vehicle> vehiclesList;
    private RecHolder.ItemClickListener mItemListener;

    public RecAdapter(ArrayList vehiclesList, RecHolder.ItemClickListener itemClickListener){
        this.vehiclesList = vehiclesList;
        this.mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_view, parent, false);

        RecHolder holder = new RecHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecHolder holder, final int position) {
        holder.location.setText(vehiclesList.get(position).getLocation());
        holder.model.setText(vehiclesList.get(position).getModel());
        holder.capacity.setText(String.valueOf(vehiclesList.get(position).getCapacity()));
        holder.price.setText(String.valueOf(vehiclesList.get(position).getPrice()));
        holder.type.setText(String.valueOf(vehiclesList.get(position).getType()));

        holder.itemView.setOnClickListener(view -> {
            mItemListener.onItemClick(vehiclesList, position);
        });

    }

    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }

}
