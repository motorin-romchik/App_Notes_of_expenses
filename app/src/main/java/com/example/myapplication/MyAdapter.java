package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList place_id, item_id, sum_id;

    public MyAdapter(Context context, ArrayList place_id, ArrayList item_id, ArrayList sum_id) {
        this.context = context;
        this.place_id = place_id;
        this.item_id = item_id;
        this.sum_id = sum_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.place_id.setText(String.valueOf(place_id.get(position)));
        holder.item_id.setText(String.valueOf(item_id.get(position)));
        holder.sum_id.setText(String.valueOf(sum_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return place_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView place_id,item_id,sum_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            place_id = itemView.findViewById(R.id.textplace);
            item_id = itemView.findViewById(R.id.textitem);
            sum_id = itemView.findViewById(R.id.textsum);
        }
    }
}
