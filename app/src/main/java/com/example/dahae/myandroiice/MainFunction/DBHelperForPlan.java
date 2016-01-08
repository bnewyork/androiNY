package com.example.dahae.myandroiice.MainFunction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperForPlan extends SQLiteOpenHelper {

    public DBHelperForPlan(Context context) {
        super(context, "Database", null, 1);
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {    }

    public void createTable(SQLiteDatabase db) { }
}
