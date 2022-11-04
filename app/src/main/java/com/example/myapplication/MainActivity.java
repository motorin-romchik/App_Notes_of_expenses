package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText place, item,sum;
    Button insert,view,delete;
    DBHelper DB;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        place = findViewById(R.id.place);
        item = findViewById(R.id.item);
        sum = findViewById(R.id.sum);


        insert = findViewById(R.id.btnInsert);
        view = findViewById(R.id.btnView);
        delete = findViewById(R.id.btnDelete);

        DB = new DBHelper(this);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity (new Intent(MainActivity.this, Userlist.class));
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double val; //to convert from string to double
                String placeTXT = place.getText().toString();
                String itemTXT = item.getText().toString();
                String sumTXT = sum.getText().toString();
                //val = Double.parseDouble(sum.getText().toString());
                //Pro = Pro + val;
                if (!placeTXT.equals("") || !itemTXT.equals("") || !sumTXT.equals("")){
                place.setText("");
                item.setText("");
                sum.setText("");

                Boolean checkinsertdata = DB.insertuserdata(placeTXT, itemTXT, sumTXT);
                   if (checkinsertdata == true) {
                       Toast.makeText(MainActivity.this, "Успех", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                   }
                   //textPro.setText(Double.toString(Pro));
                   //textPro.setText("200");
                }
                else {
                    Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String placeTXT = place.getText().toString();
                DB.delete("Userdetails");

                /*Boolean checkdeletedata = DB.deleteuserdata (ageTXT);
                if (checkdeletedata == true){
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
                }*/
            }


        } );

    }


}