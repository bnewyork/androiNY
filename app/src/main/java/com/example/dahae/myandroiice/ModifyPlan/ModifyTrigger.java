package com.example.dahae.myandroiice.ModifyPlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dahae.myandroiice.Adapter.PlanListAdapter;
import com.example.dahae.myandroiice.Adapter.PlanListItem;
import com.example.dahae.myandroiice.Intro.IntroClose;
import com.example.dahae.myandroiice.Intro.IntroShakeLR;
import com.example.dahae.myandroiice.Intro.IntroShakeUPDOWN;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.TriggerForBattery;
import com.example.dahae.myandroiice.Triggers.TriggerForMap;
import com.example.dahae.myandroiice.Triggers.TriggerForPhoneReception;
import com.example.dahae.myandroiice.Triggers.TriggerForSMS;
import com.example.dahae.myandroiice.Triggers.TriggerForTime;

import java.util.ArrayList;
import java.util.List;

public class ModifyTrigger extends AppCompatActivity { //http://apphappy.tistory.com/10 참조!

    String triggerName = "";
    String triggerInfo = "";

    String arr1[] = { "Wi-Fi 켜짐", "Wi-Fi 꺼짐", "화면 켜짐", "화면 꺼짐", "소리모드", "진동모드", "무음모드", // 7
            "데이터네트워크 켜짐", "데이터네트워크 꺼짐", "블루투스 켜짐", "블루투스 꺼짐", // 8-11
            "SMS 수신시", "비행기모드 켜짐","비행기모드 꺼짐", "통화 종료시", "전화 수신시",  "충전기 연결시", "충전기 해제시", // 12-18
            "이어폰 연결시", "이어폰 연결 해제시", "베터리 N이하", "베터리 N이상", // 19-22
            "양쪽 흔들기","폰 뒤집기","위아래 흔들기","밝기 센서","근접 센서","장소", "요일/시간"//23-29
    };

    ListView triggerplan;
    PlanListAdapter planAdapter;
    /**
     * Retrieving this instance's number from its arguments.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        triggerplan = (ListView) findViewById(R.id.planList);
        planAdapter = new PlanListAdapter(this);

        for(int i = 0 ; i < arr1.length; i++) {
            planAdapter.addItem(new PlanListItem(arr1[i]));//고쳐
        }
        triggerplan.setAdapter(planAdapter);

        triggerplan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //와이파이 켜짐
                    triggerName = "WifiOn";
                } else if (position == 1) {
                    //와이파이 꺼짐
                    triggerName = "WifiOff";
                } else if (position == 2) {
                    //화면 켜짐
                    triggerName = "ScreenOn";
                } else if (position == 3) {
                    //화면 꺼짐
                    triggerName = "ScreenOff";
                } else if (position == 4) {
                    //소리모드
                    triggerName = "Sound";
                } else if (position == 5) {
                    //진동모드
                    triggerName = "Vibration";
                } else if (position == 6) {
                    //무음모드
                    triggerName = "Silence";
                } else if (position == 7) {
                    //데이터네트워크 켜짐
                    triggerName = "DataOn";
                } else if (position == 8) {
                    //데이터네트워크 꺼짐
                    triggerName = "DataOff";
                } else if (position == 9) {
                    //블루투스 켜짐
                    triggerName = "BluetoothOn";
                } else if (position == 10) {
                    //블루투스 꺼짐
                    triggerName = "BluetoothOff";
                } else if (position == 11) {
                    //문자 수신시
                    Intent intentSMS = new Intent(getApplicationContext(), TriggerForSMS.class);
                    startActivityForResult(intentSMS, 0);
                    triggerName = "SMSreceiver";
                } else if (position == 12) {
                    //비행기모드 바꼈을때
                    triggerName = "AirplaneModeOn";
                } else if (position == 13) {
                    //비행기모드 바꼈을때
                    triggerName = "AirplaneModeOff";
                } else if (position == 14) {
                    //전화 발신
                    triggerName = "CallEnded";
                } else if (position == 15) {
                    Intent intentPhone = new Intent(getApplicationContext(), TriggerForPhoneReception.class);
                    startActivityForResult(intentPhone, 0);
                    triggerName = "PhoneReception";
                } else if (position == 16) {
                    //전원 연결시
                    triggerName = "PowerConnected";
                } else if (position == 17) {
                    //전원 해제시
                    triggerName = "PowerDisConnected";
                } else if (position == 18) {
                    //이어폰 연결시
                    triggerName = "EarphoneIn";
                } else if (position == 19) {
                    //이어폰 해제시
                    triggerName = "EarphoneOut";
                } else if (position == 20) {
                    Intent intentBattery = new Intent(getApplicationContext(), TriggerForBattery.class);
                    intentBattery.putExtra("Battery", "Low");
                    startActivityForResult(intentBattery, 0);
                    triggerName = "LowBattery";

                } else if (position == 21) {
                    Intent intentBattery = new Intent(getApplicationContext(), TriggerForBattery.class);
                    intentBattery.putExtra("Battery", "Full");
                    startActivityForResult(intentBattery, 0);
                    triggerName = "FullBattery";
                } else if (position == 22) {
                    Intent intentS = new Intent(getApplicationContext(), IntroShakeLR.class);
                    startActivity(intentS);
                    triggerName = "SensorLR";
                } else if (position == 23) {
                    triggerName = "UpsideDown";
                } else if (position == 24) {
                    Intent intentS = new Intent(getApplicationContext(), IntroShakeUPDOWN.class);
                    startActivity(intentS);
                    triggerName = "SensorUPDOWN";
                } else if (position == 25) {
                    triggerName = "SensorBright";
                } else if (position == 26) {
                    Intent intentS = new Intent(getApplicationContext(), IntroClose.class);
                    startActivity(intentS);
                    triggerName = "SensorClose";

                } else if (position == 27) {
                    Intent intentMap = new Intent(getApplicationContext(), TriggerForMap.class);
                    startActivityForResult(intentMap, 0);
                    triggerName = "Location";
                } else if (position == 28) {
                    Intent intentTime = new Intent(getApplicationContext(), TriggerForTime.class);
                    startActivityForResult(intentTime, 0);
                    triggerName = "Time";
                }
                intent();
            }
        });
    }

    public void intent(){

        Intent intent = getIntent();
        intent.putExtra("triggerName", triggerName);
        if(!triggerInfo.equals("")) {
            intent.putExtra("triggerInfo", triggerInfo);
        }
        intent.setAction("ModifyTrigger");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){


            if(data.getAction().toString().equals("Time")) {
                Log.d(MainActivity.TAG, "Time in onActivityResult");
                Bundle extra = data.getExtras();

                boolean[] week = extra.getBooleanArray("mTriggerInfo_week");
                Long triggerTime= data.getLongExtra("mTriggerInfo_time", 0);

                for(int i =1; i<week.length ; i++)
                    triggerInfo += week[i]+".";

                triggerInfo += "+"+ triggerTime+"+";

            } else if(data.getAction().toString().equals("BrightUp")) {

                Log.d(MainActivity.TAG,"BrightUp in Trigger");
                triggerInfo= data.getStringExtra("mTriggerInfo");

                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo);
            } else if(data.getAction().toString().equals("BrightDown")) {

                Log.d(MainActivity.TAG,"BrightDown in Trigger");
                triggerInfo= data.getStringExtra("mTriggerInfo");

            } else if(data.getAction().toString().equals("SMSreceiver")) {

                Log.d(MainActivity.TAG,"SMSreceiver");
                triggerInfo= data.getStringExtra("mTriggerInfo");

            } else if(data.getAction().toString().equals("PhoneReception")) {

                Log.d(MainActivity.TAG,"PhoneReception");
                triggerInfo= data.getStringExtra("mTriggerInfo");
            } else if(data.getAction().toString().equals("LowBattery")) {

                Log.d(MainActivity.TAG, "LowBattery in Trigger");
                triggerInfo = data.getStringExtra("mTriggerInfo");
            } else if(data.getAction().toString().equals("FullBattery")) {

                Log.d(MainActivity.TAG, "FullBattery in Trigger");
                triggerInfo = data.getStringExtra("mTriggerInfo");

            }else if (data.getAction().toString().equals("Map")) {
                Log.d(MainActivity.TAG, "Map in Trigger");
                triggerInfo = data.getStringExtra("mTriggerInfo");

            }
        }
    }
}