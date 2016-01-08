package com.example.dahae.myandroiice.ModifyPlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dahae.myandroiice.Actions.ActionForActivation;
import com.example.dahae.myandroiice.Actions.ActionForCall;
import com.example.dahae.myandroiice.Actions.ActionForNotify;
import com.example.dahae.myandroiice.Actions.ActionForSMS;
import com.example.dahae.myandroiice.Actions.ActionForTextToVoice;
import com.example.dahae.myandroiice.Actions.ActionForVolume;
import com.example.dahae.myandroiice.Actions.AppInfoActivity;
import com.example.dahae.myandroiice.Adapter.PlanListAdapter;
import com.example.dahae.myandroiice.Adapter.PlanListItem;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;
import java.util.List;

public class ModifyAction extends AppCompatActivity { //http://apphappy.tistory.com/10 참조!

    String actionName = "";
    String actionInfo = "";
    String arr2[] = { "Wi-Fi 켜기", "Wi-Fi 끄기", "소리모드로 전환", "진동모드로 전환", "무음모드로 전환",// 5
            "데이터네트워크 켜기", "데이터네트워크 끄기", "블루투스 켜기", "블루투스 끄기", //6-9
            "번호읽어주기", "문자메세지 읽어주기",// 10-11
            "카메라x","텍스트읽어주기","후레쉬", "즐겨찾기","녹음", //12-16
            "벨볼륨바꾸기","음악볼륨바꾸기","전화걸기","메세지 보내기","알림메세지(Notification)","바탕화면가기","잠금해제(*)" //17-23
            ,"명령 활성화 시키기", "명령 비활성화 시키기" // 24-25`
    };

    ListView actionplan;
    PlanListAdapter planAdapter;
    /**
     * Retrieving this instance's number from its arguments.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        actionplan = (ListView) findViewById(R.id.planList);
        planAdapter = new PlanListAdapter(this);

        for(int i = 0 ; i < arr2.length; i++) {
            planAdapter.addItem(new PlanListItem(arr2[i]));//고쳐
        }
        actionplan.setAdapter(planAdapter);

        actionplan.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * 액션 설정
                 */
                    if (position == 0){
                        //와이파이 켜기
                        actionName = "WifiOn";
                    } else if(position == 1){
                        //와이파이 끄기
                        actionName = "WifiOff";
                    } else if(position == 2){
                        //화면 켜기
                        actionName = "Sound";
                    } else if(position == 3){
                        //화면 끄기
                        actionName = "Vibration";
                    } else if(position == 4){
                        //소리모드로 전환
                        actionName = "Silence";
                    } else if(position == 5){
                        //진동모드로 전환
                        actionName = "DataOn";
                    } else if(position == 6){
                        //무음모드로 전환
                        actionName = "DataOff";
                    } else if(position == 7){
                        //블루투스 켜기
                        actionName = "BluetoothOn";
                    } else if(position == 8){
                        //블루투스 끄기
                        actionName = "BluetoothOff";
                    } else if (position == 9) {
                        actionName = "TellPhoneNum";
                        actionInfo +="TellPhoneNumInfo"+"/";
                    } else if (position == 10) {
                        actionName = "TellSMS";
                        actionInfo += "TellSMSInfo"+"/";
                    } else if (position == 11) {
                        actionName = "Camera";
                    } else if (position == 12) {
                        Intent intent= new Intent(getApplicationContext(), ActionForTextToVoice.class);
                        startActivityForResult(intent, 0);
                        actionName = "TextToVoice";
                    } else if (position == 13) {
                        actionName = "Flash";
                    } else if (position == 14) {
                        Intent intent= new Intent(getApplicationContext(), AppInfoActivity.class);
                        startActivityForResult(intent, 0);
                        actionName = "Bookmark";
                    } else if (position == 15) {
                        actionName = "AudioRecorder";
                    } else if (position == 16) {
                        Intent intent= new Intent(getApplicationContext(), ActionForVolume.class);
                        intent.putExtra("type", "Ring");
                        startActivityForResult(intent, 0);
                        actionName = "VolumeRing";
                    } else if (position == 17) {
                        Intent intent= new Intent(getApplicationContext(), ActionForVolume.class);
                        intent.putExtra("type", "Music");
                        startActivityForResult(intent, 0);
                        actionName = "VolumeMusic";
                    } else if (position == 18) {
                        Intent intentCall= new Intent(getApplicationContext(), ActionForCall.class);
                        startActivityForResult(intentCall, 0);
                        actionName = "Call";
                    }else if (position == 19) {
                        Intent intentSMS= new Intent(getApplicationContext(), ActionForSMS.class);
                        startActivityForResult(intentSMS, 0);
                        actionName = "SendingSMS";
                    }else if (position == 20) {
                        Intent intentNot= new Intent(getApplicationContext(),  ActionForNotify.class);
                        startActivityForResult(intentNot, 0);
                        actionName = "Notification";
                    }else if (position == 21) {
                        actionName = "HomeScreen";
                    }else if (position == 22) {
                        actionName = "keyLock";
                    }else if (position == 23) {
                        actionName = "Plantrue";
                        Intent intentAct= new Intent(getApplicationContext(),  ActionForActivation.class);
                        startActivityForResult(intentAct, 0);
                    }else if (position == 24) {
                        actionName = "Planfalse";
                        Intent intentAct= new Intent(getApplicationContext(),  ActionForActivation.class);
                        startActivityForResult(intentAct, 0);
                    }
                intent();
            }


        });
    }

    public void intent(){

        Intent intent = getIntent();
        intent.putExtra("actionName", actionName);
        intent.putExtra(" actionInfo",  actionInfo);

        intent.setAction("ModifyAction");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(data.getAction().toString().equals("Call")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Call ActionInfo  " +actionInfo);
            }else if(data.getAction().toString().equals("SMS")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "SMS ActionInfo  " + actionInfo);
            }else if(data.getAction().toString().equals("TextToVoice")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "TextToVoice " + actionInfo);
            }else if(data.getAction().toString().equals("Volume")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Volume " + actionInfo);
            }else if(data.getAction().toString().equals("Bookmark")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Bookmark " + actionInfo);
            }else if(data.getAction().toString().equals("Notification")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Notification ;" + actionInfo);
            }else if(data.getAction().toString().equals("Activation")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Activation ;" + actionInfo);
            }
        }
        Log.d(MainActivity.TAG, "onActivityResult In Action ; " + actionInfo);
    }

}