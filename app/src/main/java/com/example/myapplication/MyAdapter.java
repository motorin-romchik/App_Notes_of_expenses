package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList place_id, item_id, sum_id;
    List<Integer> mSelectedIds ;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }


    //добавил
    public MyAdapter(Context context, ArrayList place_id, ArrayList item_id, ArrayList sum_id) {
        this.context = context;
        this.place_id = place_id;
        this.item_id = item_id;
        this.sum_id = sum_id;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View inflatedView = layoutInflater.inflate(R.layout.userentry,parent,false);
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);

        return new MyViewHolder(v,listener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        private ImageView imageView;


          public MyViewHolder(@NonNull View itemView,OnItemClickListener listener) {

            super(itemView);
            place_id = itemView.findViewById(R.id.textplace);
            item_id = itemView.findViewById(R.id.textitem);
            sum_id = itemView.findViewById(R.id.textsum);
            imageView = itemView.findViewById(R.id.btnDelete1);

              SharedPreferences sharedPreferences = context.getSharedPreferences("myprefs",Context.MODE_PRIVATE);
              final SharedPreferences.Editor editor = sharedPreferences.edit();
              imageView.setOnClickListener(new View.OnClickListener(){

                  @Override
                  public void onClick(View view) {
                        listener.OnItemClick(getAdapterPosition());
                  }
              });

        }
        //get the selected items

    }
}
