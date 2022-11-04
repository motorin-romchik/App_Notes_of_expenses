
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> place,item,sum;
    DBHelper DB;
    MyAdapter adapter;
    TextView textPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        DB = new DBHelper(this);
        place = new ArrayList<>();
        item = new ArrayList<>();
        sum = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this,place,item, sum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();

    }

    @SuppressLint("ResourceAsColor")
    private void displaydata() {

        Cursor cursor = DB.getdata();
        textPro = findViewById(R.id.textPro);
        Double rez= Double.valueOf(0);
        String srez;
        if (cursor.getCount()==0) {
            Toast.makeText(Userlist.this, "Список пуст", Toast.LENGTH_SHORT).show();
            return ;
        }
        else {
            while(cursor.moveToNext())
            {
                place.add(cursor.getString(0));
                item.add(cursor.getString(1));
                sum.add(cursor.getString(2));
                srez = cursor.getString(2);

                try {

                    Double.parseDouble(srez);
                    rez += Double.parseDouble(srez) ;

                } catch (NumberFormatException o){


                }



            }
        }
        if (rez >= 10000){
            textPro.setTextColor(Color.parseColor("#CC0000"));
        }
        else {
            textPro.setTextColor(Color.parseColor("#03DAC5"));

        }
        textPro.setText(Double.toString(rez)+" ₽");


    }
}