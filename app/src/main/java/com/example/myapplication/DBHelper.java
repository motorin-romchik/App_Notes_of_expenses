package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;


public class DBHelper<result> extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "Userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
//        DB.execSQL("create Table Userdetails(id INTEGER ,place TEXT, item TEXT, sum TEXT)");
                DB.execSQL("create Table Notes(time TEXT, place TEXT, item TEXT, sum TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Notes");
//        onCreate(DB);//создание новой версии таблицы после ужадения старой таблицы
//        DB.execSQL(String.valueOf(SQL_CREATE_ENTRIES));

    }

    public Boolean insertuserdata(String place, String item , String sum)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Calendar calendar = Calendar.getInstance();
        String d = String.valueOf(calendar.get(Calendar.DATE));
        String mo = String.valueOf((calendar.get(Calendar.MONTH))+1);
        String y = String.valueOf(calendar.get(Calendar.YEAR));
        String h = String.valueOf(calendar.get(Calendar.HOUR));
        String m = String.valueOf(calendar.get(Calendar.MINUTE));
        String s = String.valueOf(calendar.get(Calendar.SECOND));

        mo = createIdeal(mo);
        d = createIdeal(d);
        h = createIdeal(h);
        m = createIdeal(m);
        s = createIdeal(s);

        String timeC = y+mo+d+h+m+s;
        contentValues.put("time",timeC);
        contentValues.put("place",place);
        contentValues.put("item",item);
        contentValues.put("sum",sum);
        timeC = null;

        long result = DB.insert("Notes",null,contentValues);

        if (result == -1)
            return false;
        else
           return true;

    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from Notes",null);
//        Cursor cursor1 = DB.rawQuery("Select id from Userdetails where id = '3'",);

        return cursor;
    }


    //...

    public boolean deleteAll(String table){
        SQLiteDatabase DB = this.getWritableDatabase();
//        DB.delete("SQLITE_SEQUENCE","NAME=?",new String []{table});
//        int affectedRows = DB.delete(table,null,null);
//        return affectedRows > 1;
        DB.delete("Notes",null,null);
        return true;

    }
    public boolean deleteParam(String keytime){
        SQLiteDatabase DB = this.getWritableDatabase();
//        DB.delete("Notes","place=? and item=? and sum=?",new String[]{p,i,s});
        DB.delete("Notes","time=?",new String[]{keytime});
//        try {
//            DB.delete("Userdetails", "item=?", new String[]{i});

//        }catch (Error | Exception e){}
//        DB.delete("Userdetails","place=? and item=? and sum=?",new String[]{"p","i","s"});
        return true;
    }
    public int deleteuserdata(int  id) {
//        //должен передеать массив id элемнтов для удаления
        SQLiteDatabase DB = this.getWritableDatabase();
        int res = DB.delete("Notes", "id = ?", new String[]{String.valueOf(id)});


        int realid = 1;
//        do {
//            if (CheckIsDataAlreadyInDBorNot(realid))
//                realid++;
//        } while (realid != id);
//
//        DB.delete("Userdetails", "id = ?", new String[]{String.valueOf(realid)});
        return realid;
    }

//        if(CheckIsDataAlreadyInDBorNot(id)){
//            DB.delete("Userdetails", "id = ?", new String[]{String.valueOf(id)});
//        }
//        else {
//            int t = 1;//строка просто по номеру при переборе
//            do {
//                if(CheckIsDataAlreadyInDBorNot(t)){
//                    t++;
//                }
//            }while(t!=id);







//        int res = DB.delete("Userdetails", "id = ?", new String[]{String.valueOf(idf)});

//            return true;
//        }
//        public void del(int d){
//            SQLiteDatabase DB = this.getWritableDatabase();
//            DB.execSQL("DELETE FROM Userdetails WHERE id='"+d +"'");
//
//
//        }
//        public boolean deleteByField(String a,String b,String c){
//            SQLiteDatabase DB = this.getWritableDatabase();
//            DB.deleteRow()
//        }
        public boolean CheckIsDataAlreadyInDBorNot(int position){
            SQLiteDatabase DB = this.getWritableDatabase();
            String thId = String.valueOf(position);
            String Query = "Select *from Notes where id = " + thId;
            Cursor cursor = DB.rawQuery(Query,null);
                if(cursor.getCount() <= 0){
                    cursor.close();
                    return false;
                }
                cursor.close();
                return true;
        }

    public int refresh(int id){
        //должен  передать id отмеченного элемента
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues refreshval = new ContentValues();
//        String id_forUp[]= id_array;
        String new_id="str",new_place="example",new_item="example",new_sum="example";

        refreshval.put("place",new_place);
        refreshval.put("item",new_item);
        refreshval.put("sum",new_sum);


//        int result = DB.update("Userdetails", refreshval,"id = ?",new String[] {String.valueOf(id)});
        int result = DB.delete("Notes","id = ?",new String[]{String.valueOf(id)});
        return result;

    }

    public String createIdeal(String val){
        int var = Integer.valueOf(val); //get int from str
        val = (var < 10) ? "0"+val: val;

        return val;

    }




}
