package com.example.dahae.myandroiice.NewPlan;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.DBHelperForPlan;

public class ShowingDB {

    public void seeInfoOfPlan(){

        Cursor cursor;

        try {
            if (MainActivity.database != null) {
                // 모든 테이블 목록(플랜 목록) 보여주기
                Cursor cursorT = MainActivity.database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

                while (cursorT.moveToNext()) {

                    if (!cursorT.getString(0).equals("android_metadata") && !cursorT.getString(0).equals("sqlite_sequence")) {

                        String Tablename = cursorT.getString(0);
                        Log.d(MainActivity.TAG, "Table name is " + Tablename);
                        cursor = MainActivity.database.rawQuery("SELECT * FROM " + Tablename, null);

                        for (int i = 0; i < cursor.getCount(); i++) {
                            if (cursor.moveToNext()) {
                                int _idDB = cursor.getInt(0);
                                String keyword = cursor.getString(1);
                                String keyword_Info = cursor.getString(2);
                                int keyword_level = cursor.getInt(3);
                                Log.d(MainActivity.TAG, "* _idDB : " + _idDB +
                                        " / keyword_level : " + keyword_level +
                                        " / keyword : " + keyword +
                                        " / keyword_Info : " + keyword_Info
                                );
                            }}}}}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
