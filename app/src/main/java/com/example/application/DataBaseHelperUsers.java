package com.example.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vnm on 2/10/2018.
 */

 class DatabaseHelperUsers extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "users.db";
    public static final String TABLE_NAME= "users_table";
    public static final String COL_1= "username";
    public static final String COL_2= "password";


    public DatabaseHelperUsers(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                +TABLE_NAME+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, username text, password text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String un, String pw){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1, un);
        contentValues.put(COL_2, pw);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
