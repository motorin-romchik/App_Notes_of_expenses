package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CheckBox;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(place TEXT, item TEXT, sum TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
        //DB.execSQL(SQL_CREATE_ENTRIES);

    }

    public Boolean insertuserdata(String place, String item , String sum)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("place",place);
        contentValues.put("item",item);
        contentValues.put("sum",sum);

        long result = DB.insert("Userdetails",null,contentValues);

        if (result == -1)
            return false;
        else
           return true;

    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails",null);
        return cursor;
    }


    //...

    public int delete(String table){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.delete("Userdetails",null,null);
    }
    /*public Boolean deleteuserdata(String age) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where age =  ?",new String []{age});
        if (cursor.getCount()>0){
            long result = DB.delete("Userdetails","name = ?",new String[] {age});
            if (result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }*/


}
