
package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> date,place,item,sum;
    List<Integer> delOb;
    private Context context;
    DBHelper DB;
    MyAdapter adapter;// объект класса
    TextView textPro;//поле текстовое под сумму
    ImageButton btnDelete,btnChange;//кнопки изменить/удалить
    EditText placeF,itemF,sumF;
    CardView block;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        DB = new DBHelper(this);
        delOb= new ArrayList<Integer>();

        date = new ArrayList<>();
        place = new ArrayList<>();
        item = new ArrayList<>();
        sum = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        ImageView imageDelete,imageChange;
        imageDelete = findViewById(R.id.btnDelete);
        imageChange = findViewById(R.id.btnChange);


        adapter = new MyAdapter(this,date,place,item, sum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
        adapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(int pos) {

            }

        });
        adapter.setOnItemClickListenerChange(new MyAdapter.OnItemClickListenerCh(){

            @Override
            public void OnItemClickCh(int pos) {
                placeF = findViewById(R.id.place);
                itemF = findViewById(R.id.item);
                sumF = findViewById(R.id.sum);

                int poz = pos++;
                String keytime_Field = date.get(poz);//key in DB
                String first_Field = place.get(poz);
                String second_Field = item.get(poz);
                String third_Field = sum.get(poz);
                DB.deleteParam(keytime_Field);

                Intent i = new Intent(Userlist.this,MainActivity.class);
                i.putExtra("PLACE",first_Field);
                i.putExtra("ITEMS",second_Field);
                i.putExtra("SUM",third_Field);
                startActivity(i);
                poz = 0;
            }
        });
        adapter.setOnItemClickListenerDelete(new MyAdapter.OnItemClickListener() {


            @Override
            public void OnItemClick(int pos) {
                int poz = pos++;
                String keytime = date.get(poz);
                String a = place.get(poz);// arraylists тоже начинаются с 1
                String b = item.get(poz);
                String c = sum.get(poz);


                int finalPoz = poz;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Userlist.this);
                alertDialog
                        .setMessage("Are You sure to DELETE?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                adapter.notifyItemChanged(finalPoz);


                                adapter.notifyItemRemoved(finalPoz);
                                DB.deleteParam(keytime);
//                                displaydata();
                                Intent intent = getIntent();
                                overridePendingTransition(0,0);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                                startActivity(new Intent(Userlist.this,Userlist.class));
                                dialogInterface.cancel();



                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();

                            }
                        });
                alertDialog.show();




                poz = 0;
                pos = 0;
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
//                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Userlist.this,MainActivity.class));


            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private void displaydata() {

        Cursor cursor = DB.getdata();
        textPro = findViewById(R.id.textPro);


        long rez= 0;
        String srez;

            while(cursor.moveToNext())
            {

                date.add(cursor.getString(0));
                place.add(cursor.getString(1));
                item.add(cursor.getString(2));
                sum.add(cursor.getString(3));
                srez = cursor.getString(3);

                try {

//                    Double.parseDouble(srez);
                    Long.parseLong(srez);
//                    Integer.parseInt(srez);
                    rez  += Long.parseLong(srez) ;

                } catch (NumberFormatException o){


                }
        }
        if (rez >= 35000)
            textPro.setTextColor(Color.parseColor("#CC0000"));
        else
            textPro.setTextColor(Color.parseColor("#03DAC5"));


        if(rez > 0)  textPro.setText(rez +" ₽");
    }
    public String createIdeal(String val){
        int var = Integer.valueOf(val); //get int from str
        val = (var < 10) ? "0"+val: val;

        return val;

    }

    public void openMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
