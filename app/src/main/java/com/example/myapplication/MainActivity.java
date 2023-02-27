package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyboardShortcutGroup;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText place, item,sum;
    TextView text;
    Button insert,delete;
    DBHelper DB;



    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        place = findViewById(R.id.place);
        item = findViewById(R.id.item);
        sum = findViewById(R.id.sum);


        insert = findViewById(R.id.btnInsert);
//        view = findViewById(R.id.btnView);
        delete = findViewById(R.id.btnDelete);


        DB = new DBHelper(this);
        Intent i = getIntent();

        String pIntent = i.getStringExtra("PLACE");
        String iIntent = i.getStringExtra("ITEMS");
        String sIntent = i.getStringExtra("SUM");

        if(pIntent != null || iIntent != null || sIntent != null){

            text.setText("Change data");
            place.setText(pIntent);
            item.setText(iIntent);
            sum.setText(sIntent);
        }



        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String placeTXT = place.getText().toString();
                String itemTXT = item.getText().toString();
                String sumTXT = sum.getText().toString();

                if (!placeTXT.equals("") || !itemTXT.equals("") || !sumTXT.equals("")) {
                    place.setText("");
                    item.setText("");
                    sum.setText("");

                    Boolean checkinsertdata = DB.insertuserdata(placeTXT, itemTXT, sumTXT);
                    if (checkinsertdata == true) {
                        Toast.makeText(MainActivity.this, "Успех", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }
//                recreate();
                startActivity(new Intent(MainActivity.this, Userlist.class));
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DB.deleteAll("Notes");

               startActivity(new Intent(MainActivity.this, Userlist.class));
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);


            }


        });

    }
}