package com.example.application;

/**
 * Created by vnm on 2/10/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelperStars extends SQLiteOpenHelper {
    public static final String DATABASE_NAME= "star.db";
    public static final String TABLE_NAME= "star_table";
    public static final String COL_1= "user";

    public DatabaseHelperStars(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                +TABLE_NAME+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USER TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertStar(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1, user);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getStars(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where user="+user,null);
        return res;
    }

}
