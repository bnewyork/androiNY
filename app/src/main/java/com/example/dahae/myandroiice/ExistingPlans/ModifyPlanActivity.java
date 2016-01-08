package com.example.dahae.myandroiice.ExistingPlans;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dahae.myandroiice.Adapter.PlanListAdapter;
import com.example.dahae.myandroiice.Adapter.PlanListItem;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.DBHelperForRecordTime;
import com.example.dahae.myandroiice.MainFunction.DBHelperForPlan;
import com.example.dahae.myandroiice.ModifyPlan.ModifyAction;
import com.example.dahae.myandroiice.ModifyPlan.ModifyPalnChecking;
import com.example.dahae.myandroiice.ModifyPlan.ModifyTrigger;
import com.example.dahae.myandroiice.NewPlan.ShowingDB;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.AlarmReceive;
import com.example.dahae.myandroiice.Triggers.MapReceive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class ModifyPlanActivity extends Activity {

    ListView TriggerListView;
    PlanListAdapter TriggerPlanAdapter;
    ListView ActionListView;
    PlanListAdapter ActionPlanAdapter;
    AlertDialog.Builder builder;
    Button ButSave;
    String PlaneName ="";

    LocationManager mLocationManager;
    MapReceive mIntentReceiver;
    String intentKey = "locationProximity";
    ArrayList mPendingIntentList;

    final CharSequence[] items = {"수정하기", "삭제하기"};

    int DialogNum;

    String mTriggerInfo ="";
    String mAtionInfo ="";
    TextView nameText;

    ChangeName ChangeName = new  ChangeName();
    ModifyPalnChecking modifyPalnChecking = new ModifyPalnChecking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyplans);

        Log.d(MainActivity.TAG, "hi modifyPlan");

        Intent intent = getIntent();
        PlaneName = intent.getStringExtra("listViewName");

        nameText = (TextView) findViewById(R.id.nameView);
        nameText.setText(PlaneName);

        TriggerListView = (ListView) findViewById(R.id.triggerList);
        TriggerPlanAdapter = new PlanListAdapter(this);
        ActionListView = (ListView) findViewById(R.id.actionList);
        ActionPlanAdapter = new PlanListAdapter(this);

        setAdapter();
        ShowingDB ShowingDB = new ShowingDB();
        ShowingDB.seeInfoOfPlan();
        ActionListView.setOnItemClickListener(mActionItemClickListener);
        TriggerListView.setOnItemClickListener(mTriggerItemClickListener);
        ButSave = (Button) findViewById(R.id.ButSave);
        ButSave.setOnClickListener(OnClickListener);
        builder = new AlertDialog.Builder(this);

    }

    private ListView.OnItemClickListener mTriggerItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String triggerForChange = TriggerPlanAdapter.items.get(position).getKeyword();
            DialogNum = position;

            builder.setTitle("다음을 골라주세요").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        if (triggerForChange.equals("AND") || triggerForChange.equals("OR"))
                            Toast.makeText(getApplicationContext(), "and와 or의 수정을 추천하지 않습니다. ", Toast.LENGTH_SHORT).show(); //고치기
                        else
                            IntentForTrigger();

                    } else if (which == 1) {
                        if (triggerForChange.equals("AND") || triggerForChange.equals("OR")) {
                            Toast.makeText(getApplicationContext(), "and와 or의 삭제를 추천하지 않습니다. ", Toast.LENGTH_SHORT).show();
                        } else {
                            if(modifyPalnChecking.TriggerChecktoDelete(TriggerPlanAdapter, DialogNum)) {
                                TriggerPlanAdapter.deleteItem(DialogNum);
                                TriggerListView.setAdapter(TriggerPlanAdapter);
                                Toast.makeText(getApplicationContext(), ChangeName.Trigger(triggerForChange) +"를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "규칙에 위배됩니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            AlertDialog alert = builder.create();
            if (!triggerForChange.equals("AND") && !triggerForChange.equals("OR")  && !triggerForChange.equals("Done")) {
                alert.show();
            }
        }
    };

    private ListView.OnItemClickListener mActionItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            DialogNum = position;
            builder.setTitle("다음을 골라주세요").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        IntentForAction();
                    } else if (which == 1) {
                        if(ActionPlanAdapter.getCount() !=1 ) {
                            ActionPlanAdapter.deleteItem(DialogNum);
                            ActionListView.setAdapter(ActionPlanAdapter);
                        }else{
                            Toast.makeText(getApplicationContext(), "행동을 더이상 삭제하지 마세요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    private View.OnClickListener OnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            List<String> listModify = new ArrayList<>();
            String triggerName = "";
            String actionName ="";

            if(TriggerPlanAdapter.getCount() == 2){
                triggerName = TriggerPlanAdapter.items.get(0).getKeyword();
                listModify.add(ChangeName.TriggerToEglish(triggerName)+"/");
            }else {
                for (int i = 0; i < TriggerPlanAdapter.getCount(); i++) {
                    triggerName = TriggerPlanAdapter.items.get(i).getKeyword();

                    if (triggerName.equals("OR"))
                        listModify.add("Or");
                    else if (triggerName.equals("AND"))
                        listModify.add("And");
                    else
                        listModify.add(ChangeName.TriggerToEglish(triggerName)+"/");
                }
            }

            for (int i = 0; i < ActionPlanAdapter.getCount(); i++)
                for (i = 0; i < ActionPlanAdapter.getCount(); i++)
                    actionName += ChangeName.ActionToEglish(ActionPlanAdapter.items.get(i).getKeyword())+"/";

            for(int i =0;i< listModify.size(); i++)
                Log.d("db",listModify.get(i));
            saveModifyDB(listModify, actionName, PlaneName, mAtionInfo, mTriggerInfo);
            Toast.makeText(getApplicationContext(), "잘 변경 되었습니다..", Toast.LENGTH_LONG).show();

            finish();
        }
    };

    public void saveModifyDB(List mlistTrigger, String mActionName, String tableName , String mActionInfo, String mTriggerInfo){

        int level_id =0;
        if(MainActivity.database != null)
            MainActivity.database.execSQL("DROP TABLE IF EXISTS " + tableName);
        if(MainActivity.databaseForRecordTime != null)
            MainActivity.databaseForRecordTime.execSQL("DELETE FROM ActivationInfoTable where planName = '" + tableName + "';");
        if(!tableName.equals("")) {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + tableName + "("
                        + " _id integer primary KEY autoincrement,"
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
            PlaneName = "";
            mAtionInfo = "";
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



    public void IntentForTrigger(){
        Intent intentForModify = new Intent(getApplicationContext(), ModifyTrigger.class);
        //intentMap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intentForModify, 0);
    }

    public void IntentForAction(){
        Intent intentForModify = new Intent(getApplicationContext(), ModifyAction.class);
        //intentMap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intentForModify, 0);
    }

    public void setAdapter() {

        Cursor cursorT = MainActivity.databaseForRecordTime.rawQuery("SELECT triggerName FROM " + PlaneName, null);
        Cursor cursorA = MainActivity.databaseForRecordTime.rawQuery("SELECT actionName FROM " + PlaneName, null);

        int i = 0;
        if (!PlaneName.isEmpty()){
            try {
                if (MainActivity.databaseForRecordTime != null) {
                    if (cursorT != null && cursorT.getCount() != 0) {
                        while (cursorT.moveToNext()) {
                            String triggerplan = cursorT.getString(0);

                            if (triggerplan != null) {
                                if (!triggerplan.equals("")) {

                                    if (!triggerplan.equals("End")){//&& !triggerplan.equals("Done")){
                                        TriggerPlanAdapter.addItem(new PlanListItem(ChangeName.Trigger(triggerplan)));
                                    }
                                }}}}

                    cursorA.moveToLast();
                    String actionplan = cursorA.getString(0);
                    if (actionplan == null || actionplan.equals("")) {
                        ;
                    } else {
                        StringTokenizer action = new StringTokenizer(actionplan, "/");
                        while (action.hasMoreTokens())
                            ActionPlanAdapter.addItem(new PlanListItem(ChangeName.Action(action.nextToken())));
                    }
                }
            } finally {
                cursorA.close();
                cursorT.close();
            }
        } else{
            Toast.makeText(getApplicationContext(), "값이 없습니다.", Toast.LENGTH_SHORT).show();
        }

        TriggerListView.setAdapter(TriggerPlanAdapter);
        ActionListView.setAdapter(ActionPlanAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String oldtriggerName= "";

        if(resultCode == Activity.RESULT_OK){
            if(data.getAction().equals("ModifyTrigger")) {

                String triggerName = data.getStringExtra("triggerName");

                if(!triggerName.equals("")) {

                    if(triggerName.equals("Time")) {
                        Log.d(MainActivity.TAG, "Time in onActivityResult");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    } else if(triggerName.equals("BrightUp")) {

                        Log.d(MainActivity.TAG,"BrightUp in Trigger");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    } else if(triggerName.equals("BrightDown")) {

                        Log.d(MainActivity.TAG,"BrightDown in Trigger");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    } else if(triggerName.equals("SMSreceiver")) {

                        Log.d(MainActivity.TAG,"SMSreceiver");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    } else if(triggerName.equals("PhoneReception")) {

                        Log.d(MainActivity.TAG,"PhoneReception");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    } else if(triggerName.equals("LowBattery")) {

                        Log.d(MainActivity.TAG, "LowBattery in Trigger");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    } else if(triggerName.equals("FullBattery")) {

                        Log.d(MainActivity.TAG, "FullBattery in Trigger");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    }else if (triggerName.equals("Map")) {
                        Log.d(MainActivity.TAG, "Map in Trigger");
                        mTriggerInfo= data.getStringExtra("triggerInfo")+"/";

                        Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + mTriggerInfo);
                    }
                    Log.d(MainActivity.TAG, "**triggerName in NewPlanTrigger" + triggerName);
                    Log.d(MainActivity.TAG, "**DialogNum in NewPlanTrigger" + DialogNum);
                    TriggerPlanAdapter.items.set(DialogNum, new PlanListItem(ChangeName.Trigger(triggerName)));//trigger값 바꾸기
                    TriggerListView.setAdapter(TriggerPlanAdapter);
                }
            } else if(data.getAction().equals("ModifyAction")) {

                String actionrName = data.getStringExtra("actionName");
                String actionInfo = data.getStringExtra("actionInfo");
                if(!actionrName.equals("")) {
                    mAtionInfo += actionInfo + "/";
                    ActionPlanAdapter.items.set(DialogNum, new PlanListItem(ChangeName.Action(actionrName))); //action값 바꾸기
                    ActionListView.setAdapter(ActionPlanAdapter);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_active_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
