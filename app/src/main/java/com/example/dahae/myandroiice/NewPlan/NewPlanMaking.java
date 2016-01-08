package com.example.dahae.myandroiice.NewPlan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.Triggers.AlarmReceive;
import com.example.dahae.myandroiice.Triggers.MapReceive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class NewPlanMaking extends AppCompatActivity {

    ShowingDB showingDB = new ShowingDB();
    NewPlanCheck newPlanCheck = new NewPlanCheck();

    ViewPager mViewPager;
    PagerAdapter mPagerAdapter;

    public NewPlanTrigger newPlanTrigger;
    public NewPlanAction newPlanAction;
    public NewPlanConfig newPlanConfig;

    String mPlanName;
    String mActionName;
    String mActionInfo;
    String mTriggerInfo;

    Intent intent;
    List<String> mlistTrigger;

    LocationManager mLocationManager;
    MapReceive mIntentReceiver;
    String intentKey = "locationProximity";
    ArrayList mPendingIntentList;

    Intent ServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

        Button buttonTrigger = (Button) findViewById(R.id.buttonTrigger);
        Button buttonAction = (Button) findViewById(R.id.buttonAction);
        Button buttonConfig = (Button) findViewById(R.id.buttonConfig);

        buttonTrigger.setOnClickListener(onClickListener);
        buttonAction.setOnClickListener(onClickListener);
        buttonConfig.setOnClickListener(onClickListener);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), newPlanTrigger,  newPlanAction, newPlanConfig);
        mViewPager.setAdapter(mPagerAdapter);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mPendingIntentList = new ArrayList();
        mIntentReceiver = new MapReceive(intentKey);
        intent = getIntent();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonTrigger){
                mViewPager.setCurrentItem(0); }
            else if(v.getId()==R.id.buttonAction){
                mViewPager.setCurrentItem(1); }
            else if(v.getId()==R.id.buttonConfig){
                mViewPager.setCurrentItem(2); }
        }
    };

    public void saveNewDB(List mlistTrigger, String mActionName, String tableName , String mActionInfo, String mTriggerInfo){

        int level_id =0;
        String[] test = (String[]) mlistTrigger.toArray(new String[mlistTrigger.size()]);

        if(!tableName.equals("")) {

            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + tableName + "("
                        + "_id integer primary KEY autoincrement,"
                        + " Keyword text,"
                        + " Keyword_info text,"
                        + " Keyword_level integer,"
                        + " Keyword_meaning text"
                        + ");");
                Log.d(MainActivity.TAG, "Table " + tableName + " created");
            }

            //트리거 저장
            Iterator iterator = mlistTrigger.iterator();

            while (iterator.hasNext()) {

                String trigger = (String) iterator.next();

                if (!trigger.contains("/")) {
                    if (trigger.contains("And")) {
                        if (MainActivity.database != null) {
                            MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) values "
                                    + "('And', " + level_id + ");");
                        } else {
                            Log.e(MainActivity.TAG, "first, you should open the table ");
                        }
                        level_id++;
                    } else if (trigger.contains("Or")) {
                        if (MainActivity.database != null) {
                            MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) VALUES "
                                    + "('Or', " + level_id + ");");
                        } else {
                            Log.e(MainActivity.TAG, "first, you should open the table ");
                        }
                        level_id++;
                    } else if (trigger.contains("Done")) {
                        MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) values "
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
                            MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level, Keyword_Info) VALUES "
                                    + "('" + TriggerForDB + "', " + level_id + ", '" + TroggerInfo + "');");
                    }
                }
            }
            if (MainActivity.database != null)
                MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) VALUES "
                        + "('Done', 0);");


            //트리거의 end인뎅 액션 먼저 넣어도 level_id가 달라서 상관없..
            if (MainActivity.database != null)
                MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) VALUES "
                        + "('End', 0);");

            //액션 저장
            try {
                if (MainActivity.database != null)
                    MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level, Keyword_Info) VALUES "
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
            mPagerAdapter.newPlanTrigger.triggerName = "";
            mPagerAdapter.newPlanTrigger.triggerInfo = "";
            mPagerAdapter.newPlanAction.actionInfo = "";
            mPagerAdapter.newPlanAction.actionName = "";
            level_id = 0;
        }
    }
    public void register(int id, double latitude, double longitude, float radius, long expiration) {

        Intent proximityIntent = new Intent(getApplicationContext(),
                MapReceive.class);
        proximityIntent.putExtra("id", id);
        proximityIntent.putExtra("latitude", latitude);
        proximityIntent.putExtra("longitude", longitude);

        PendingIntent pending = getPendingIntent(proximityIntent);

        mLocationManager.addProximityAlert(latitude, longitude, radius, expiration,  pending);
        mPendingIntentList.add(pending);
    }


    private void startLocationService() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GPSListener gpsListener = new GPSListener();

        long minTime = 100000;
        float minDistance = 0;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);

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
    public void onStop() {
        super.onStop();
        //unregister();
    }
    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            Log.i(MainActivity.TAG, "Latitude : "+ latitude + ", Longitude:"+ longitude + " InGPS");
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    public void alramSet(boolean[] week ,Long triggerTime){

        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

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
        Intent intent = new Intent(getApplicationContext(), AlarmReceive.class);
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

    private PendingIntent getPendingIntent(Intent intent) {
        double d = Math.random() *10000;
        int i = Integer.parseInt(String.valueOf(Math.round(d)));

        PendingIntent pIntent = PendingIntent.getBroadcast(this, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_save){

            mPlanName = mPagerAdapter.newPlanConfig.planName.getText().toString();
            if(newPlanCheck.NoSameName(mPlanName)) {
                if (newPlanCheck.AsSyntax(mlistTrigger, mActionName, mActionInfo)) {

                    saveNewDB(mlistTrigger, mActionName, mPlanName, mActionInfo, mTriggerInfo);
                    showingDB.seeInfoOfPlan();

                    Toast.makeText(getApplicationContext(), "서비스를 시작합니다.", Toast.LENGTH_LONG).show();
                    ServiceIntent = new Intent(getApplicationContext(), MyTrigger.class);
                    startService(ServiceIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "상황 규칙문이 규칙에 위배됩니다.", Toast.LENGTH_LONG);
                }
                finish();
            }else{
                mPlanName ="";
                Toast.makeText(getApplicationContext(), "같은 계획 이름이 있습니다.", Toast.LENGTH_LONG);
            }
        }
        else if (id == R.id.action_cancel){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            //만들던 플랜 취소! -> 한꺼번에 저장 안하다가 저장 버튼 누르면 저장해도 되고, 누르는대로 저장하다가 X눌렀을때 지우는 방법도???
        }
        return super.onOptionsItemSelected(item);
    }

}
