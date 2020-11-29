package com.example.aahar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public String TABLE ="USER_TABLE";
    public String NUMBER="NUMBER";
    public String PASSWORD="PASSWORD";
    public String NAME="NAME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtabelstatement="CREATE TABLE "+TABLE+"("+NUMBER+" TEXT PRIMARY KEY ,"+PASSWORD+" TEXT,"+NAME+" TEXT)";
        db.execSQL(createtabelstatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean Add(Data data){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(NUMBER,data.getNumber());
        cv.put(PASSWORD,data.getPassword());
        cv.put(NAME,data.getName());
        MainActivity.number=data.getNumber();
        long insert = db.insert(TABLE,null,cv);
        if(insert==1) {
            return true;
        }
        else{
            return false;
        }
    }
    public ArrayList<Data> checkUser(){
        ArrayList<Data> returnData= new ArrayList<>();
        String ss="";
        String QueryString ="SELECT * FROM "  +TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor result = db.rawQuery(QueryString,null);
        if(result.moveToFirst()==true){
            do {
                String number =result.getString(0);
                String password=result.getString(1);
                String name= result.getString(2);
                Data newdata =new Data(number,password,name);
                MainActivity.number=newdata.getNumber();
                returnData.add(newdata);

            } while (result.moveToNext());


        }
        else {

        }

        result.close();
        db.close();
        return returnData;
    }
    public boolean delete(){
        SQLiteDatabase db =this.getWritableDatabase();
        String queryString ="DELETE FROM "+ TABLE;
        Cursor result =db.rawQuery(queryString,null);
        if(result.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

    }
}

