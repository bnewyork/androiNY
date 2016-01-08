package com.example.dahae.myandroiice.MainFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperForRecordTime extends SQLiteOpenHelper{

    public DBHelperForRecordTime(Context context) {
        super(context, "InfoDatabase", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //changeTable(db);
    }

    public void createTable(SQLiteDatabase db){

    }
}