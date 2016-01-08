package com.example.dahae.myandroiice.Intro;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.DBHelperForRecordTime;
import com.example.dahae.myandroiice.MainFunction.DBHelperForPlan;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.AlarmReceive;
import com.example.dahae.myandroiice.Triggers.MapReceive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class IntroDatabase  extends Activity {

    CheckBox CheckBox1, CheckBox2, CheckBox3, CheckBox4;
    CheckBox CheckBox5, CheckBox6, CheckBox7, CheckBox8;
    ImageButton Button;
    Intent intent;

    LocationManager mLocationManager;
    MapReceive mIntentReceiver;
    String intentKey = "locationProximity";
    ArrayList mPendingIntentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introformain4);

        CheckBox1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox4 = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox5 = (CheckBox) findViewById(R.id.checkBox5);
        CheckBox6 = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox7 = (CheckBox) findViewById(R.id.checkBox7);
        CheckBox8 = (CheckBox) findViewById(R.id.checkBox8);
        Button = (ImageButton) findViewById(R.id.imageButton);

        intent = getIntent();

       // createDatabase();

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> mlistTrigger = new ArrayList<>();
                String mActionName = "";
                String tableName = "";
                String mActionInfo = "";
                String mTriggerInfo = "";

                if (CheckBox1.isChecked()) {
                    {
                        mlistTrigger.add("EarphoneIn/");
                        mActionName = "Bookmark/";
                        mActionInfo = "com.sec.android.app.music";
                        tableName = "이어폰꽂으면음악어플켜기";
                        saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, "empty");
                    }
                    {
                        mlistTrigger.add("EarphoneIn/");
                        mTriggerInfo = "empty";
                        mActionName = "VolumeMusic/";
                        mActionInfo = "7";
                        tableName = "이어폰꽂으면음악볼륨7";
                        saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, mTriggerInfo);
                    }
                    {
                        mlistTrigger.add("EarphoneOut/");
                        mActionName = "VolumeMusic/";
                        mActionInfo = "15";
                        tableName = "이어폰뽑으면음악볼륨최대";
                        saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, mTriggerInfo);
                    }
                }
                if (CheckBox2.isChecked()) {

                    mlistTrigger.add("SMSreceiver/");
                    mTriggerInfo = "01049479094";
                    mActionName = "TellPhoneNum/";
                    mActionInfo = "TellPhoneNumInfo/";
                    tableName = "수신번호읽어주기";
                    saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, mTriggerInfo);

                    mlistTrigger.add("SMSreceiver/");
                    mTriggerInfo = "01049479094";
                    mActionName = "TellSMS/";
                    mActionInfo = "TellSMSInfo/";
                    tableName = "문자내용읽어주기";
                    saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, mTriggerInfo);

                }
                if (CheckBox3.isChecked()) {

                }
                if (CheckBox4.isChecked()) {

                }
                if (CheckBox5.isChecked()) {
                }
                if (CheckBox6.isChecked()) {
                }
                if (CheckBox7.isChecked()) {

                    mlistTrigger.add("LowBattery/");
                    mTriggerInfo = "35";
                    mActionName = "DataOff/WifiOn/";
                    mActionInfo = "";
                    tableName = "배터리적으면데이터끄기";
                    saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, mTriggerInfo);

                    mlistTrigger.add("PowerDisConnected/");
                    mTriggerInfo = "empty";
                    mActionName = "WifiOn/DataOff/ ";
                    mActionInfo = "";
                    tableName = "충전해제시데이터끄기";
                    saveDatabase(mlistTrigger, mActionName, tableName, mActionInfo, mTriggerInfo);

                }
                IntentFomain();
            }
        });


    }

    public void IntentFomain(){
        SharedPreferences pref = getSharedPreferences("pref",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("IntroOnlyOnetime", true);
        editor.commit();
        IntroDatabase.this.finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent ServiceIntent = new Intent(getApplicationContext(), MyTrigger.class);
        startService(ServiceIntent);
    }

    public void saveDatabase(List mlistTrigger, String mActionName, String tableName , String mActionInfo, String mTriggerInfo){

        int level_id =0;

        if(!tableName.equals("")) {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + tableName + "("
                        + " _id integer primary KEY autoincrement,"
                        + " triggerName text,"
                        + " actionName text,"
                        + " level_id integer,"
                        + " actionInfo text,"
                        + " triggerInfo text"
                        + ");");
                Log.d(MainActivity.TAG, "Table " + tableName + " created");
            }
            //트리거 저장
            Iterator iterator = mlistTrigger.iterator();

            while (iterator.hasNext()) {

                String trigger = (String) iterator.next();
                // Log.d(TAG, trigger + " ;" + level_id);

                if (!trigger.contains("/")) {
                    if (trigger.contains("And")) {
                        if (MainActivity.database != null) {
                            MainActivity.database.execSQL("INSERT INTO " + tableName + "(triggerName, level_id) values "
                                    + "('And', " + level_id + ");");
                        } else {
                            Log.e(MainActivity.TAG, "first, you should open the table ");
                        }
                        level_id++;
                    } else if (trigger.contains("Or")) {
                        if (MainActivity.database != null) {
                            MainActivity.database.execSQL("INSERT INTO " + tableName + "(triggerName, level_id) VALUES "
                                    + "('Or', " + level_id + ");");
                        } else {
                            Log.e(MainActivity.TAG, "first, you should open the table ");
                        }
                        level_id++;
                    } else if (trigger.contains("Done")) {
                        MainActivity.database.execSQL("INSERT INTO " + tableName + "(triggerName, level_id) values "
                                + "('Done', " + level_id + ");");
                        level_id--;
                    }
                } else if (trigger.contains("/")) {

                    StringTokenizer st = new StringTokenizer(trigger, "/");
                    StringTokenizer stInfo = new StringTokenizer(mTriggerInfo, "/");

                    while (st.hasMoreTokens()) {

                        String TriggerForDB = st.nextToken();
                        String TroggerInfo = "empty";

                        if(TriggerForDB.equals("Time")|| TriggerForDB.equals("BrightDown") ||
                                TriggerForDB.equals("BrightUp") || TriggerForDB.equals("PhoneReception")||
                                TriggerForDB.equals("LowBattery") || TriggerForDB.equals("FullBattery")
                                || TriggerForDB.equals("Location")){

                            Log.d(MainActivity.TAG, "triggerInfo  " + mTriggerInfo);


                            if(stInfo.hasMoreTokens()) {
                                if (TriggerForDB.equals("Time")) {
                                    StringTokenizer stFortime = new StringTokenizer(stInfo.nextToken(), "+");
                                    boolean[] week = new boolean[7];
                                    StringTokenizer stWeek = new StringTokenizer(stFortime.nextToken(), ".");

                                    for (int i = 0; i < 7; i++) {
                                        String Week = stWeek.nextToken();
                                        //    Log.d(TAG,"Week " + Week);
                                        if (Week.equals("true"))
                                            week[i] = true;
                                        else
                                            week[i] = false;
                                    }
                                    Long triggerTime = Long.valueOf(stFortime.nextToken()).longValue();
                                    alramSet(week, triggerTime);
                                } else if (TriggerForDB.equals("Location")) {
                                    StringTokenizer stFormap = new StringTokenizer(stInfo.nextToken(), "+");
                                    if (stFormap.hasMoreTokens()) {
                                        startLocationService();
                                        Double latitudeForRegister = Double.parseDouble(stFormap.nextToken());
                                        Double longitudeForRegister = Double.parseDouble(stFormap.nextToken());
                                        register(1001, latitudeForRegister, longitudeForRegister, 200, -1);
                                        mIntentReceiver = new MapReceive(intentKey);
                                        registerReceiver(mIntentReceiver, mIntentReceiver.getFilter());
                                        Log.d(MainActivity.TAG, "Proximity Start!");
                                    }
                                }else {
                                    TroggerInfo = stInfo.nextToken();
                                }
                            }
                        }

                        if (MainActivity.database != null)
                            MainActivity.database.execSQL("INSERT INTO " + tableName + "(triggerName, level_id, triggerInfo) VALUES "
                                    + "('" + TriggerForDB + "', " + level_id + ", '" + TroggerInfo + "');");
                    }
                }
            }
            if (MainActivity.database != null)
                MainActivity.database.execSQL("INSERT INTO " + tableName + "(triggerName, level_id) VALUES "
                        + "('Done', 0);");


            //트리거의 end인뎅 액션 먼저 넣어도 level_id가 달라서 상관없..
            if (MainActivity.database != null)
                MainActivity.database.execSQL("INSERT INTO " + tableName + "(triggerName, level_id) VALUES "
                        + "('End', 0);");





            //액션 저장
            try {
                if (MainActivity.database != null)
                    MainActivity.database.execSQL("INSERT INTO " + tableName + "(actionName, level_id, actionInfo) VALUES "
                            + "('" + mActionName + "', -1, '" + mActionInfo + "');");

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                if(MainActivity.databaseForRecordTime != null) {
                    MainActivity.databaseForRecordTime.execSQL("INSERT INTO ActivationInfoTable(planName, activation) VALUES "
                            + "('" + tableName + "', 'true');");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //트리거 리스트,level_id 초기화해쥰당
            mlistTrigger.clear();
        }
    }



    public void register(int id, double latitude, double longitude, float radius, long expiration) {

        Intent proximityIntent = new Intent(getApplicationContext(),
                MapReceive.class);
        proximityIntent.putExtra("id", id);
        proximityIntent.putExtra("latitude", latitude);
        proximityIntent.putExtra("longitude", longitude);

        PendingIntent pending = getPendingIntent(proximityIntent);

        mLocationManager.addProximityAlert(latitude, longitude, radius, expiration, pending);
        Log.d(MainActivity.TAG, "latitude~pending: " + latitude + longitude + radius + expiration + pending);
        mPendingIntentList.add(pending);
    }

    public void onStop() {
        super.onStop();
        //unregister();
    }

    private void unregister() {
        if (mPendingIntentList != null) {
            for (int i = 0; i < mPendingIntentList.size(); i++) {
                PendingIntent curIntent = (PendingIntent) mPendingIntentList.get(i);
                mLocationManager.removeProximityAlert(curIntent);
                mPendingIntentList.remove(i);
            }
        }

        if (mIntentReceiver != null) {
            unregisterReceiver(mIntentReceiver);
            mIntentReceiver = null;
        }
    }

    private void startLocationService() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GPSListener gpsListener = new GPSListener();

        long minTime = 100000;
        float minDistance = 0;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);

    }

    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            Log.i(MainActivity.TAG, "Latitude : "+ latitude + ", Longitude:"+ longitude + " InGPS");
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    public void alramSet(boolean[] week ,Long triggerTime){

        AlarmManager alarm = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);

        boolean isRepeat = false;
        int len = week.length;
        long oneday = 24 * 60 * 60 * 1000;// 24시간

        Log.d(MainActivity.TAG,"alramSet()");
        for (int i = 1; i < len; i++){
            if (week[i]) {
                isRepeat = true;
                break;
            }
        }
        Intent intent = new Intent(getApplicationContext(),
                AlarmReceive.class);
        //   intent.putExtra(AlarmReceive.ACTION_ALARM,
        //           AlarmReceive.ACTION_ALARM);

        if(isRepeat){

            intent.putExtra("Week", week);
            intent.putExtra("Repeat", true);
            PendingIntent pending = getPendingIntent(intent);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, oneday, pending);
            Log.d(MainActivity.TAG, "isRepeat");
        }else{

            intent.putExtra("Week", week);
            intent.putExtra("Repeat", false);
            PendingIntent pending = getPendingIntent(intent);
            alarm.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
            Log.d(MainActivity.TAG, "one time");
        }
    }

    private PendingIntent getPendingIntent(Intent intent)
    {
        double d = Math.random() *10000;
        int i = Integer.parseInt(String.valueOf(Math.round(d)));

        PendingIntent pIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }

}
