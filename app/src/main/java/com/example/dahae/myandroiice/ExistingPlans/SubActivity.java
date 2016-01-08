package com.example.dahae.myandroiice.ExistingPlans;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dahae.myandroiice.Adapter.PlanItem;
import com.example.dahae.myandroiice.MainActivity;

import java.util.ArrayList;

public class SubActivity {

    public ArrayList<PlanItem> getdisplayListInFalse(){

        PlanItem planItem;
        ArrayList<PlanItem> planList= new ArrayList<>();
        Cursor cursor = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);

        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String planName = cursor.getString(1);
                        String activationState = cursor.getString(2);

                        if(activationState.equals("false")){
                            planItem = new PlanItem(planName, planName, false);
                            planList.add(planItem);
                        }
                    }
                }
            }
        } finally {
            cursor.close();
        }
        return planList;
    }

    public ArrayList<PlanItem> getdisplayListInTrue(){

        PlanItem planItem;
        ArrayList<PlanItem> planList= new ArrayList<>();
        Cursor cursor = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);

        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String planName = cursor.getString(1);
                        String activationState = cursor.getString(2);
                      //  Log.d(TAG, "planName: " + planName + " / activationState: " + activationState);
                        if(activationState.equals("true")){
                            planItem = new PlanItem(planName, planName, true);
                            planList.add(planItem);
                        }
                    }
                }
            }
        } finally {
            cursor.close();
        }
        return planList;
    }

    public void copyPlanAsTrue(String oldName){

        String newName = oldName.concat("_복사본");

        try {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + newName + "("
                        + " _id integer primary KEY autoincrement,"
                        + " Keyword text,"
                        + " Keyword_info text,"
                        + " Keyword_level integer,"
                        + " Keyword_meaning text"
                        + ");");
                MainActivity.database.execSQL("INSERT INTO " + newName + " SELECT * FROM " + oldName + ";");

                if(MainActivity.databaseForRecordTime != null) {
                    MainActivity.databaseForRecordTime.execSQL("CREATE TABLE if not exists ActivationInfoTable("
                            + " _id integer primary KEY autoincrement,"
                            + "planName text,"
                            + "activation text"
                            + ");");
                    MainActivity.databaseForRecordTime.execSQL("INSERT INTO ActivationInfoTable(planName, activation) values "
                            + "('" + newName + "', 'true');");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyPlanAsFalse(String oldName){

        String newName = oldName.concat("_복사본");

        try {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + newName + "("
                        + " _id integer primary KEY autoincrement,"
                        + " Keyword text,"
                        + " Keyword_info text,"
                        + " Keyword_level integer,"
                        + " Keyword_meaning text"
                        + ");");
                MainActivity.database.execSQL("INSERT INTO " + newName + " SELECT * FROM " + oldName + ";");

                if(MainActivity.databaseForRecordTime != null) {
                    MainActivity.databaseForRecordTime.execSQL("CREATE TABLE if not exists ActivationInfoTable("
                            + " _id integer primary KEY autoincrement,"
                            + "planName text,"
                            + "activation text"
                            + ");");
                    MainActivity.databaseForRecordTime.execSQL("INSERT INTO ActivationInfoTable(planName, activation) values "
                            + "('" + newName + "', 'false');");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePlan(String Name ){

        try {
            if (MainActivity.database != null)
                MainActivity.database.execSQL("DROP TABLE IF EXISTS " + Name);
            if(MainActivity.databaseForRecordTime != null)
                MainActivity.databaseForRecordTime.execSQL("DELETE FROM ActivationInfoTable where planName = '" + Name + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePlanName(String oldName, String newName ){
        try {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("ALTER TABLE " + oldName+ " RENAME TO " + newName);
            }
            if (MainActivity.databaseForRecordTime != null)
                MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable SET planName = '" + newName + "' "
                        + "where planName = '" + oldName + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
