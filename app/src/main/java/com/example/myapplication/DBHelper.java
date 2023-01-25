package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper<result> extends SQLiteOpenHelper {
//    private static final  int SQL_CREATE_ENTRIES = 1;

//    int id = 0; //id счетчик для инициализации элементов в БД

    public DBHelper(Context context) {
        super(context, "Userdata.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
//        DB.execSQL("create Table Userdetails(id INTEGER ,place TEXT, item TEXT, sum TEXT)");
                DB.execSQL("create Table Userdetails(place TEXT, item TEXT, sum TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
//        onCreate(DB);//создание новой версии таблицы после ужадения старой таблицы
//        DB.execSQL(String.valueOf(SQL_CREATE_ENTRIES));

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
//        Cursor cursor1 = DB.rawQuery("Select id from Userdetails where id = '3'",);

        return cursor;
    }


    //...

    public boolean deleteAll(String table){
        SQLiteDatabase DB = this.getWritableDatabase();
//        DB.delete("SQLITE_SEQUENCE","NAME=?",new String []{table});
//        int affectedRows = DB.delete(table,null,null);
//        return affectedRows > 1;
        DB.delete("Userdetails",null,null);
        return true;

    }
    public boolean deleteParam(String p, String i, String s){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("Userdetails","place=? and item=? and sum=?",new String[]{p,i,s});

//        try {
//            DB.delete("Userdetails", "item=?", new String[]{i});

//        }catch (Error | Exception e){}
//        DB.delete("Userdetails","place=? and item=? and sum=?",new String[]{"p","i","s"});
        return true;
    }
    public int deleteuserdata(int  id) {
//        //должен передеать массив id элемнтов для удаления
        SQLiteDatabase DB = this.getWritableDatabase();
        int res = DB.delete("Userdetails", "id = ?", new String[]{String.valueOf(id)});

//        while(!CheckIsDataAlreadyInDBorNot(id)){
//            id++;
//        }
//        if(CheckIsDataAlreadyInDBorNot(id)) {
//            int res = DB.delete("Userdetails", "id = ?", new String[]{String.valueOf(id)});
//        }
//>-
//        SQLiteDatabase DB = this.getWritableDatabase();
//        int f = id;
//        int result = DB.delete("Userdetails","id = ?", new String[]{String.valueOf(id)});
//        if(result <= 1){
//            do{
//                f++;
//                result = DB.delete("Userdetails","id = ?", new String[]{String.valueOf(f)});
//
//        }while(result > 1);
//        }
        //<-

//        try{
//        int result = DB.delete("Userdetails","id = ?", new String[]{String.valueOf(id)});}
//        catch (Exception| Error e){
//            System.out.println("Cool");
//        }

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
            String Query = "Select *from Userdetails where id = " + thId;
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
        int result = DB.delete("Userdetails","id = ?",new String[]{String.valueOf(id)});
        return result;

    }




}
