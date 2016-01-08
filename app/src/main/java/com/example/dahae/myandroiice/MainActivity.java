package com.example.dahae.myandroiice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dahae.myandroiice.ExistingPlans.ActivePlanActivity;
import com.example.dahae.myandroiice.ExistingPlans.SleepingPlanActivity;
import com.example.dahae.myandroiice.MainFunction.CheckPlan;
import com.example.dahae.myandroiice.MainFunction.DBHelperForRecordTime;
import com.example.dahae.myandroiice.MainFunction.DBHelperForPlan;
import com.example.dahae.myandroiice.MainFunction.MyAction;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.Intro.IntroDatabase;
import com.example.dahae.myandroiice.Intro.IntroForMain;
import com.example.dahae.myandroiice.NewPlan.NewPlanMaking;

public class MainActivity extends AppCompatActivity {

    public static String TAG ="[ANDROI-ICE]";

    public static SQLiteDatabase database;
    public static DBHelperForPlan dbHelperForPlan;
    public static SQLiteDatabase databaseForRecordTime;
    public static DBHelperForRecordTime dbHelperForRecordTime;

    Button activePlanBut;
    Button sleepingPlanBut;
    Button recordBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase();

        activePlanBut = (Button) findViewById(R.id.ButtonActive);
        sleepingPlanBut = (Button) findViewById(R.id.ButtonSleep);
        recordBut = (Button) findViewById(R.id.ButtonRecord);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        Boolean chkintro = pref.getBoolean("IntroCheck", false);
        Boolean chkOnlyOnetime = pref.getBoolean("IntroOnlyOnetime", false);

        Log.d(TAG, "Intro +" + chkintro + "/" + chkOnlyOnetime);
        if (!chkintro) {
            Intent introIntent = new Intent(getApplicationContext(), IntroForMain.class);
            startActivity(introIntent);
        }

        if (!chkOnlyOnetime) {
            Intent onetimeIntent = new Intent(getApplicationContext(), IntroDatabase.class);
            startActivity(onetimeIntent);
        }
        recordBut.setText("실행된 기록 :)");
        recordBut.setTextSize(15);

        setActivePlanNum();
        setSleepingPlanNum();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setActivePlanNum();
        setSleepingPlanNum();
    }

    public void setActivePlanNum(){

        Cursor cursor = databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable WHERE activation = 'true'", null);
        int num = cursor.getCount();

        if(num  < 1)
            num = 0;

        activePlanBut.setText("활성 명령"+"\n"+Integer.toString(num) + " 개");
        activePlanBut.setTextSize(40);
    }

    public void setSleepingPlanNum(){

        Cursor cursor =databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable WHERE activation = 'false'", null);
        int num = cursor.getCount();

        if(num  < 1)
            num = 0;

        sleepingPlanBut.setText("휴면 명령" + Integer.toString(num)+ " 개");
        sleepingPlanBut.setTextSize(15);
    }

    public void createDatabase(){

        try {
            //플랜을 저장,수정하기 위한 데이터베이스
            dbHelperForPlan = new DBHelperForPlan(getApplicationContext());
            database = dbHelperForPlan.getWritableDatabase();

            //플랜의 추가정보(활성화여부, 기록) 저장을 위한 데이터베이스
            dbHelperForRecordTime = new DBHelperForRecordTime((getApplicationContext()));
            databaseForRecordTime = dbHelperForRecordTime.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseForRecordTime.execSQL("CREATE TABLE if not exists ActivationInfoTable("
                + " _id integer primary KEY autoincrement,"
                + "planName text,"
                + "activation text"
                + ");");
        Log.d(TAG, "Table \'ActivationInfoTable\' created");

        databaseForRecordTime.execSQL("CREATE TABLE if not exists RecordTimeTable("
                + " _id integer primary KEY autoincrement,"
                + "planName text,"
                + "recordTime text"
                + ");");
        Log.d(TAG, "Table \'RecordTimeTable\' created");

    }

    public void onButtonActivePlanClicked(View v){
        Intent intentActivPlans = new Intent(getApplicationContext(), ActivePlanActivity.class);
        startActivityForResult(intentActivPlans, 0);
    }

    public void onButtonSleepingPlanClicked(View v){
        Intent intentSleepingPlans = new Intent(getApplicationContext(), SleepingPlanActivity.class);
        startActivityForResult(intentSleepingPlans, 0);
    }
    public void onButtonRecordClicked(View v){
        Intent intentRecordPlans = new Intent(getApplicationContext(), RecordTimeActivity.class);
        startActivityForResult(intentRecordPlans, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new){
            Intent intentPlusButton = new Intent(getApplicationContext(), NewPlanMaking.class);
            startActivityForResult(intentPlusButton, 0);
            return true;
        } else if (id == R.id.action_start_service) {
            Intent ServiceIntent = new Intent(getApplicationContext(), MyTrigger.class);
            startService(ServiceIntent);
            return true;

        } else if (id == R.id.action_stop_service) {
            stopService(new Intent(MainActivity.this, MyTrigger.class));
            stopService(new Intent(MainActivity.this, MyAction.class));
            stopService(new Intent(MainActivity.this, CheckPlan.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setActivePlanNum();
        setSleepingPlanNum();
    }
}