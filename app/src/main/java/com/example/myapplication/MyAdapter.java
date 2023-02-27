package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList place_id, item_id, sum_id;
    private ArrayList date;
    List<Integer> mSelectedIds;
    private OnItemClickListener listener;
    private OnItemClickListenerCh listenerCh;
    private OnItemLongClickListener longListener;

    public interface OnItemLongClickListener {//interface for long click
        void OnItemLongClick(int posistion); // to show two buttons del and change

    }
    public void setOnItemLongClickListener(OnItemLongClickListener clickListener){
        longListener = clickListener;
    }

    public interface OnItemClickListener {//interface for click button
        void OnItemClick(int position);
    }
    public interface OnItemClickListenerCh {
        void OnItemClickCh(int position);//interface for Change button
    }

    public void setOnItemClickListenerDelete(OnItemClickListener clickListener) {
        listener = clickListener;
    }

    public void setOnItemClickListenerChange(OnItemClickListenerCh clickListener){
        listenerCh = clickListener;
    }


//    public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
//        listener = clickListener;
//    }


    //передал arraylist с данными
    public MyAdapter(Context context, ArrayList date, ArrayList place_id, ArrayList item_id, ArrayList sum_id) {
        this.context = context;
        this.date = date;
        this.place_id = place_id;
        this.item_id = item_id;
        this.sum_id = sum_id;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);

        return new MyViewHolder(v, listenerCh,listener, longListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String month = String.valueOf((calendar.get(Calendar.MONTH)) + 1);
        String DBtime = String.valueOf(date.get(position));

        String DBymd = DBtime.substring(0, 8);//get DataBase year+month+day
        String d = DBtime.substring(6, 8);
        String m = DBtime.substring(4, 6);
        String h = DBtime.substring(8, 10);
        String min = DBtime.substring(10, 12);

        day = createIdeal(day);
        month = createIdeal(month);
        if(DBymd.equals(year+month+day)) {
            holder.date.setText("Today "+h+":" +min );

        } else {
            holder.date.setText(d+"/"+m);
        }


        holder.place_id.setText(String.valueOf(place_id.get(position)));
        //закинул по одному значения из arlist в каждое поле по позиции
        holder.item_id.setText(String.valueOf(item_id.get(position)));
        holder.sum_id.setText(String.valueOf(sum_id.get(position)));

    }

    @Override
    public int getItemCount() {
        return place_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView place_id, item_id, sum_id;
        TextView date;
        private ImageView imageDelete,imageChange;
        private CardView block;

        public MyViewHolder(@NonNull View itemView,OnItemClickListenerCh listenerCh, OnItemClickListener listener, OnItemLongClickListener longListener) {

            super(itemView);
            date = itemView.findViewById(R.id.date);//field for date
            place_id = itemView.findViewById(R.id.textplace);
            item_id = itemView.findViewById(R.id.textitem);
            sum_id = itemView.findViewById(R.id.textsum);
            imageDelete = itemView.findViewById(R.id.btnDelete);
            imageChange = itemView.findViewById(R.id.btnChange);
            block = itemView.findViewById(R.id.block);


            SharedPreferences sharedPreferences = context.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            imageChange.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    listenerCh.OnItemClickCh(getAdapterPosition());
                }
            });
            imageDelete.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View view) {
                    listener.OnItemClick(getAdapterPosition());


                }
            });

            block.setOnLongClickListener(new View.OnLongClickListener(){
                Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

                @Override
                public boolean onLongClick(View view) {
                    vibe.vibrate(50);
                    longListener.OnItemLongClick(getAdapterPosition());
                    imageDelete.setVisibility(View.VISIBLE);
                    imageChange.setVisibility(View.VISIBLE);
                    return true;
                }
            });
            imageDelete.setVisibility(View.INVISIBLE);
            imageChange.setVisibility(View.INVISIBLE);


        }
        //get the selected items

    }

    public String createIdeal(String val) {
        int var = Integer.valueOf(val); //get int from str
        val = (var < 10) ? "0" + val : val;

        return val;

    }

}
