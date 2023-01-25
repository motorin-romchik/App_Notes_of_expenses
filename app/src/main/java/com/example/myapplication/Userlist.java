
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> place,item,sum;
    List<Integer> delOb;
    private Context context;
    DBHelper DB;
    MyAdapter adapter;// объект класса
    TextView textPro;//поле текстовое под сумму
    ImageButton btnDelete,btnChange;//кнопки изменить/удалить


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        DB = new DBHelper(this);
        delOb= new ArrayList<Integer>();

        place = new ArrayList<>();
        item = new ArrayList<>();
        sum = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);


//        btnDelete = findViewById(R.id.btnDel);
//        btnChange = findViewById(R.id.btnCha);

        adapter = new MyAdapter(this,place,item, sum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                //pos начинается с 0
                int poz = pos++;
                String a = place.get(poz);// arraylists тоже начинаются с 1
                String b = item.get(poz);
                String c = sum.get(poz);

//                place.remove(pos);
//                item.remove(pos);
//                sum.remove(pos);
                adapter.notifyItemChanged(poz);
                DB.deleteParam(a,b,c);
                recreate();


                poz = 0;
                pos = 0;

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Userlist.this,MainActivity.class));
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);


            }
        });

//        btnChange.setOnClickListener(new View.OnClickListener() {
//            int count = 0;
//
//            @Override
//            public void onClick(View view) {
//                 delOb= new ArrayList<Integer>();
//                 delOb = adapter.getSelectedIds();
//                 int []ids = new int[delOb.size()];
//                 for(int i =0;i < ids.length;i++){
//                     ids[i] = delOb.get(i);
//                     count++;
//                 }
//
//                //добавить id отмеченного элемента для изменения
//                if(ids.length == 1){
//                    DB.refresh(ids[0]);
//                    startActivity(new Intent (Userlist.this,Userlist.class));
//
//                }
//
//
//            }
//        });
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                delOb= new ArrayList<Integer>();
//                delOb = adapter.getSelectedIds();//array with 0 or 1
//                int []ids = new int[delOb.size()];//ids[] have a 0 or 1 if Checked!
//                for(int i =0;i < ids.length;i++){
//                    ids[i] = delOb.get(i);
//                }
//
////                int []best = new int[ids.length];
////                for(int i=0;i<best.length;i++){
////                    if(ids[i] == 1){
////                        best[i] = i+1;
////                    }
////                    else{;}
////                }
//
//                DB.deleteuserdata(ids);
//
//                startActivity(new Intent(Userlist.this, Userlist.class));
//
//            }
//       });


    }

    @SuppressLint("ResourceAsColor")
    private void displaydata() {

        Cursor cursor = DB.getdata();
        textPro = findViewById(R.id.textPro);


        Double rez= Double.valueOf(0);
        String srez;

//        btnDelete.setVisibility(View.INVISIBLE);
//        btnChange.setVisibility(View.INVISIBLE);//выключаю кнопки

//        if (cursor.getCount()==0) {
//            Toast.makeText(Userlist.this, "Список пуст", Toast.LENGTH_SHORT).show();
//            return ;
//        }
//        else {
            while(cursor.moveToNext())
            {
//                delOb.add(cursor.getString(1));
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
        if (rez >= 10000)
            textPro.setTextColor(Color.parseColor("#CC0000"));
        else
            textPro.setTextColor(Color.parseColor("#03DAC5"));

        if(rez > 0)  textPro.setText(Double.toString(rez)+" ₽");
    }
}