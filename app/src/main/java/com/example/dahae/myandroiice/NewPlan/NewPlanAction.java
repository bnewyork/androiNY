package com.example.dahae.myandroiice.NewPlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.dahae.myandroiice.Actions.ActionForActivation;
import com.example.dahae.myandroiice.Actions.ActionForCall;
import com.example.dahae.myandroiice.Actions.ActionForNotify;
import com.example.dahae.myandroiice.Actions.ActionForSMS;
import com.example.dahae.myandroiice.Actions.ActionForTextToVoice;
import com.example.dahae.myandroiice.Actions.ActionForVolume;
import com.example.dahae.myandroiice.Actions.AppInfoActivity;
import com.example.dahae.myandroiice.ExistingPlans.ChangeName;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

public class NewPlanAction extends ListFragment {

    int fragNum;
    Button buttonActionEnd;

    String actionName = "";
    String actionName_beforeAdding = "";
    String actionInfo = "";
    String actionInfo_beforeAdding = "";

    ChangeName ChangeName = new ChangeName();

    String arr2[] = { "Wi-Fi 켜기", "Wi-Fi 끄기", "소리모드로 전환", "진동모드로 전환", "무음모드로 전환",// 5
            "데이터네트워크 켜기", "데이터네트워크 끄기", "블루투스 켜기", "블루투스 끄기", //6-9
            "번호읽어주기", "문자메세지 읽어주기",// 10-11
            "카메라x","텍스트읽어주기","후레쉬", "즐겨찾기","녹음", //12-16
            "벨볼륨바꾸기","음악볼륨바꾸기","전화걸기","메세지 보내기","알림메세지(Notification)","바탕화면가기","잠금해제(*)" //17-23
            ,"명령 활성화 시키기", "명령 비활성화 시키기" // 24-25`
    };

    public static NewPlanAction init(int val) {
        NewPlanAction fragment = new NewPlanAction();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Retrieving this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragNum = getArguments() != null ? getArguments().getInt("val") : 1;

    }

    /**
     * The Fragment's UI is a list.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.new_plan_action, null);

        buttonActionEnd = (Button) layoutView.findViewById(R.id.buttonActionEnd);
        buttonActionEnd.setOnClickListener(l);

        return layoutView;
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonActionEnd){
                NewPlanMaking newPlanMaking = (NewPlanMaking) getActivity();
                newPlanMaking.mActionName = actionName;
                newPlanMaking.mActionInfo =  actionInfo;
                newPlanMaking.mViewPager.setCurrentItem(2);
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (fragNum == 1) {
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, arr2));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i("FragmentList", "pageNum: " + fragNum + " Item clicked: " + position);

        /**
         * 액션 설정
         */
        if (fragNum == 1){
            if (position == 0){
                //와이파이 켜기
                actionName_beforeAdding = "WifiOn";
            } else if(position == 1){
                //와이파이 끄기
                actionName_beforeAdding = "WifiOff";
            } else if(position == 2){
                //화면 켜기
                actionName_beforeAdding = "Sound";
            } else if(position == 3){
                //화면 끄기
                actionName_beforeAdding = "Vibration";
            } else if(position == 4){
                //소리모드로 전환
                actionName_beforeAdding = "Silence";
            } else if(position == 5){
                //진동모드로 전환
                actionName_beforeAdding = "DataOn";
            } else if(position == 6){
                //무음모드로 전환
                actionName_beforeAdding = "DataOff";
            } else if(position == 7){
                //블루투스 켜기
                actionName_beforeAdding = "BluetoothOn";
            } else if(position == 8){
                //블루투스 끄기
                actionName_beforeAdding = "BluetoothOff";
            } else if (position == 9) {
                actionName_beforeAdding = "TellPhoneNum";
                actionInfo +="TellPhoneNumInfo"+"/";
            } else if (position == 10) {
                actionName_beforeAdding = "TellSMS";
                actionInfo += "TellSMSInfo"+"/";
            } else if (position == 11) {
                actionName_beforeAdding = "Camera";
            } else if (position == 12) {
                Intent intent= new Intent(getActivity(), ActionForTextToVoice.class);
                startActivityForResult(intent, 0);
                actionName_beforeAdding = "TextToVoice";
            } else if (position == 13) {
                actionName_beforeAdding = "Flash";
            } else if (position == 14) {
                Intent intent= new Intent(getActivity(), AppInfoActivity.class);
                startActivityForResult(intent, 0);
                actionName_beforeAdding = "Bookmark";
            } else if (position == 15) {
                actionName_beforeAdding = "AudioRecorder";
            } else if (position == 16) {
                Intent intent= new Intent(getActivity(), ActionForVolume.class);
                intent.putExtra("type", "Ring");
                startActivityForResult(intent, 0);
                actionName_beforeAdding = "VolumeRing";
            } else if (position == 17) {
                Intent intent= new Intent(getActivity(), ActionForVolume.class);
                intent.putExtra("type", "Music");
                startActivityForResult(intent, 0);
                actionName_beforeAdding = "VolumeMusic";
            } else if (position == 18) {
                Intent intentCall= new Intent(getActivity(), ActionForCall.class);
                startActivityForResult(intentCall, 0);
                actionName_beforeAdding = "Call";
            }else if (position == 19) {
                Intent intentSMS= new Intent(getActivity(), ActionForSMS.class);
                startActivityForResult(intentSMS, 0);
                actionName_beforeAdding = "SendingSMS";
            }else if (position == 20) {
                Intent intentNot= new Intent(getActivity(),  ActionForNotify.class);
                startActivityForResult(intentNot, 0);
                actionName_beforeAdding = "Notification";
            }else if (position == 21) {
                actionName_beforeAdding = "HomeScreen";
            }else if (position == 22) {
                actionName_beforeAdding = "keyLock";
            }else if (position == 23) {
                actionName_beforeAdding = "Plantrue";
                Intent intentAct= new Intent(getActivity(),  ActionForActivation.class);
                startActivityForResult(intentAct, 0);
            }else if (position == 24) {
                actionName_beforeAdding = "Planfalse";
                Intent intentAct= new Intent(getActivity(),  ActionForActivation.class);
                startActivityForResult(intentAct, 0);
            }
            actionName += actionName_beforeAdding+"/";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(data.getAction().toString().equals("Call")) {
                actionInfo_beforeAdding = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Call ActionInfo  " +actionInfo_beforeAdding);
            }else if(data.getAction().toString().equals("SMS")) {
                actionInfo_beforeAdding = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "SMS ActionInfo  " + actionInfo_beforeAdding);
            }else if(data.getAction().toString().equals("TextToVoice")) {
                actionInfo_beforeAdding = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "TextToVoice " + actionInfo_beforeAdding);
            }else if(data.getAction().toString().equals("Volume")) {
                actionInfo_beforeAdding= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Volume " + actionInfo_beforeAdding);
            }else if(data.getAction().toString().equals("Bookmark")) {
                actionInfo_beforeAdding= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Bookmark " + actionInfo_beforeAdding);
            }else if(data.getAction().toString().equals("Notification")) {
                actionInfo_beforeAdding= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Notification ;" + actionInfo_beforeAdding);
            }else if(data.getAction().toString().equals("Activation")) {
                actionInfo_beforeAdding= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Activation ;" + actionInfo_beforeAdding);
             }
        }
        actionInfo += actionInfo_beforeAdding+"/";

    }


}