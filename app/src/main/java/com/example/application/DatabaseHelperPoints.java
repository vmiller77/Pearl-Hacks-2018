package com.example.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vnm on 2/10/2018.
 */

public class DatabaseHelperPoints extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "point.db";
    public static final String TABLE_NAME= "point_table";
    public static final String COL_1= "latitude";
    public static final String COL_2= "longitude";
    public static final String COL_3= "name";


    public DatabaseHelperPoints(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                +TABLE_NAME+
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LONGITUDE REAL, LATITUDE REAL, NAME TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPoint(double lat, double lon, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1, lat);
        contentValues.put(COL_2, lon);
        contentValues.put(COL_3, name);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getAllPoints(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
