package com.example.dahae.myandroiice.NewPlan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.Trees;
import com.example.dahae.myandroiice.ExistingPlans.ChangeName;
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

public class NewPlanTrigger extends Fragment {

    Button buttonOr;
    Button buttonAnd;
    Button buttonDone;
    Button buttonTriggerEnd;
//    Button buttonDelete;

    String triggerName = "";
    String triggerName_beforeAdding = "";
    String triggerInfo = "";
    String triggerInfo_beforeAdding = "";

    ListView triggerListView;
    ListView treeListView;
    TriggerAdapter triggerAdapter;
    TreeAdapter treeAdapter;

    ArrayList<String> listTrigger = new ArrayList<>();     // 실제 처리를 위한(NewPlanActivity로 넘겨주기 위한) ArrayList
    ArrayList<Trees> treeList = new ArrayList<>();

    ChangeName ChangeName = new ChangeName();

    String arr1[] = { "Wi-Fi 켜짐", "Wi-Fi 꺼짐", "화면 켜짐", "화면 꺼짐", "소리모드", "진동모드", "무음모드", // 7
            "데이터네트워크 켜짐", "데이터네트워크 꺼짐", "블루투스 켜짐", "블루투스 꺼짐", // 8-11
            "SMS 수신시", "비행기모드 켜짐","비행기모드 꺼짐", "통화 종료시", "전화 수신시",  "충전기 연결시", "충전기 해제시", // 12-18
            "이어폰 연결시", "이어폰 연결 해제시", "베터리 N이하", "베터리 N이상", // 19-22
            "양쪽 흔들기","폰 뒤집기","위아래 흔들기","밝기 센서","근접 센서","장소", "요일/시간"//23-29
    };

    public NewPlanTrigger() {
    }

    public static NewPlanTrigger init(int pageNumber) {
        NewPlanTrigger fragment = new NewPlanTrigger();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.new_plan_trigger, null);

        /* Tree ListView Setting */
        treeListView = (ListView) layoutView.findViewById(R.id.tree_list);
        treeAdapter = new TreeAdapter(getActivity(), R.layout.trigger_tree_item, treeList);

        /* Trigger ListView Setting */
        triggerListView = (ListView) layoutView.findViewById(R.id.triggerListView);
        triggerAdapter = new TriggerAdapter(container.getContext());
        for(int i = 0; i < arr1.length; i++)
            triggerAdapter.addItem(new TriggerItem(arr1[i]));
        triggerListView.setAdapter(triggerAdapter);
        triggerListView.setOnItemClickListener(mTriggerItemClickListener);

        /* Button Setting */
        buttonOr = (Button) layoutView.findViewById(R.id.buttonOr);
        buttonOr.setOnClickListener(l);
        buttonAnd = (Button) layoutView.findViewById(R.id.buttonAnd);
        buttonAnd.setOnClickListener(l);
        buttonDone = (Button) layoutView.findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(l);
        buttonTriggerEnd = (Button) layoutView.findViewById(R.id.buttonTriggerEnd);
        buttonTriggerEnd.setOnClickListener(l);

        return layoutView;
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.buttonAnd){
                if(!triggerName.equals("")) {
                    addToList(triggerName);
                }
                addToList("And");
                addToTree("And");
            }
            else if(v.getId()== R.id.buttonOr){
                if(!triggerName.equals("")){
                    addToList(triggerName);
                }
                addToList("Or");
                addToTree("Or");
                triggerName="";
            }
            else if(v.getId()== R.id.buttonDone){
                if(!triggerName.equals("")){
                    addToList(triggerName);
                }
                addToList("Done");
                addToTree("Done");
                triggerName = "";
            }
            else if(v.getId()== R.id.buttonTriggerEnd){
                NewPlanMaking newPlanMaking = (NewPlanMaking) getActivity();
                if(triggerName.equals("")){
                    newPlanMaking.mlistTrigger = listTrigger;
                    newPlanMaking.mTriggerInfo =  triggerInfo;
                }else {
                    addToList(triggerName);
                    newPlanMaking.mlistTrigger = listTrigger;
                    newPlanMaking.mTriggerInfo =  triggerInfo;
                }
                triggerName = "";
                newPlanMaking.mViewPager.setCurrentItem(1);
            }
        }
    };

    private AdapterView.OnItemClickListener mTriggerItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {
                //와이파이 켜짐
                triggerName_beforeAdding = "WifiOn";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 1) {
                //와이파이 꺼짐
                triggerName_beforeAdding = "WifiOff";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 2) {
                //화면 켜짐
                triggerName_beforeAdding = "ScreenOn";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 3) {
                //화면 꺼짐
                triggerName_beforeAdding = "ScreenOff";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 4) {
                //소리모드
                triggerName_beforeAdding = "Sound";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 5) {
                //진동모드
                triggerName_beforeAdding = "Vibration";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 6) {
                //무음모드
                triggerName_beforeAdding = "Silence";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 7) {
                //데이터네트워크 켜짐
                triggerName_beforeAdding = "DataOn";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 8) {
                //데이터네트워크 꺼짐
                triggerName_beforeAdding = "DataOff";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 9) {
                //블루투스 켜짐
                triggerName_beforeAdding = "BluetoothOn";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 10) {
                //블루투스 꺼짐
                triggerName_beforeAdding = "BluetoothOff";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 11) {
                //문자 수신시
                Intent intentSMS = new Intent(getActivity(), TriggerForSMS.class);
                startActivityForResult(intentSMS, 0);
                triggerName_beforeAdding = "SMSreceiver";
            } else if (position == 12) {
                //비행기모드 바꼈을때
                triggerName_beforeAdding = "AirplaneModeOn";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 13) {
                //비행기모드 바꼈을때
                triggerName_beforeAdding = "AirplaneModeOff";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 14) {
                //전화 발신
                triggerName_beforeAdding = "CallEnded";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 15) {
                Intent intentPhone = new Intent(getActivity(), TriggerForPhoneReception.class);
                startActivityForResult(intentPhone, 0);
                triggerName_beforeAdding = "PhoneReception";
            } else if (position == 16) {
                //전원 연결시
                triggerName_beforeAdding = "PowerConnected";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 17) {
                //전원 해제시
                triggerName_beforeAdding = "PowerDisConnected";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 18) {
                //이어폰 연결시
                triggerName_beforeAdding = "EarphoneIn";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 19) {
                //이어폰 해제시
                triggerName_beforeAdding = "EarphoneOut";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 20) {
                Intent intentBattery = new Intent(getActivity(), TriggerForBattery.class);
                intentBattery.putExtra("Battery", "Low");
                startActivityForResult(intentBattery, 0);
                triggerName_beforeAdding = "LowBattery";

            } else if (position == 21) {
                Intent intentBattery = new Intent(getActivity(), TriggerForBattery.class);
                intentBattery.putExtra("Battery", "Full");
                startActivityForResult(intentBattery, 0);
                triggerName_beforeAdding = "FullBattery";
            } else if (position == 22) {
                Intent intentS = new Intent(getActivity(), IntroShakeLR.class);
                startActivity(intentS);
                triggerName_beforeAdding = "SensorLR";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 23) {
                triggerName_beforeAdding = "UpsideDown";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 24) {
                Intent intentS = new Intent(getActivity(), IntroShakeUPDOWN.class);
                startActivity(intentS);
                triggerName_beforeAdding = "SensorUPDOWN";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 25) {
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                triggerName_beforeAdding = "SensorBright";
            } else if (position == 26) {
                Intent intentS = new Intent(getActivity(), IntroClose.class);
                startActivity(intentS);
                triggerName_beforeAdding = "SensorClose";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
            } else if (position == 27) {
                Intent intentMap = new Intent(getActivity(), TriggerForMap.class);
                startActivityForResult(intentMap, 0);
                triggerName_beforeAdding = "Location";
            } else if (position == 28) {
                Intent intentTime = new Intent(getActivity(), TriggerForTime.class);
                startActivityForResult(intentTime, 0);
                triggerName_beforeAdding = "Time";
            }
            triggerName += triggerName_beforeAdding + "/";
            treeListView.setAdapter(treeAdapter);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        triggerInfo_beforeAdding ="";
        if(resultCode == Activity.RESULT_OK){

            if(data.getAction().toString().equals("Time")) {
                Log.d(MainActivity.TAG, "Time in onActivityResult");
                Bundle extra = data.getExtras();

                boolean[] week = extra.getBooleanArray("mTriggerInfo_week");
                Long triggerTime= data.getLongExtra("mTriggerInfo_time", 0);

                for(int i =1; i<week.length ; i++)
                    triggerInfo_beforeAdding += week[i]+".";

                triggerInfo_beforeAdding += "+"+ triggerTime+"+";
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger " + triggerInfo_beforeAdding);
            } else if(data.getAction().toString().equals("BrightUp")) {

                Log.d(MainActivity.TAG,"BrightUp in Trigger");
                triggerInfo_beforeAdding= data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            } else if(data.getAction().toString().equals("BrightDown")) {

                Log.d(MainActivity.TAG,"BrightDown in Trigger");
                triggerInfo_beforeAdding= data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            } else if(data.getAction().toString().equals("SMSreceiver")) {

                Log.d(MainActivity.TAG,"SMSreceiver");
                triggerInfo_beforeAdding= data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            } else if(data.getAction().toString().equals("PhoneReception")) {

                Log.d(MainActivity.TAG,"PhoneReception");
                triggerInfo_beforeAdding= data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            } else if(data.getAction().toString().equals("LowBattery")) {

                Log.d(MainActivity.TAG, "LowBattery in Trigger");
                triggerInfo_beforeAdding = data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            } else if(data.getAction().toString().equals("FullBattery")) {

                Log.d(MainActivity.TAG, "FullBattery in Trigger");
                triggerInfo_beforeAdding = data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            }else if (data.getAction().toString().equals("Map")) {
                Log.d(MainActivity.TAG, "Map in Trigger");
                triggerInfo_beforeAdding = data.getStringExtra("mTriggerInfo");
                addToTree(ChangeName.Trigger(triggerName_beforeAdding));
                Log.d(MainActivity.TAG, "triggerInfo in NewPlanTrigger" + triggerInfo_beforeAdding);
            }
        }
        triggerInfo += triggerInfo_beforeAdding+"/";
    }

    private class TreeAdapter extends ArrayAdapter<Trees> {

        public TreeAdapter(Context context, int textViewResourceId, ArrayList<Trees> treeList) {
            super(context, textViewResourceId, treeList);
            treeList = new ArrayList<>();
            treeList.addAll(treeList);
        }

        private class ViewHolder {
            TextView name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.trigger_tree_item, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.triggerTreeItem);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Trees trees = treeList.get(position);
            holder.name.setText(trees.getName());
            holder.name.setTag(trees);

            return convertView;
        }
    }

    public void addToList(String triggerName){
        Log.d(MainActivity.TAG, "hi addToList");
        listTrigger.add(triggerName);
    }

//    public void deleteFromList(){
//        //Log.d(TAG, "hi deleteFromList " + );
//
//        String triggerDelete = "";
//
//        if(listTrigger.size() > 0) {
//            int lastIndex = listTrigger.size()-1;
//          //  String delete = listTrigger.get(lastIndex);
//
//            Log.d(TAG,"1 "+ lastIndex);
//            listTrigger.remove(lastIndex);
//            if(!triggerName.equals("")){
//                Log.d(TAG,"2");
//                StringTokenizer st = new StringTokenizer(triggerName, "/");
//                for (int i = 0; i < st.countTokens() - 1; i++)
//                    triggerDelete += st.nextToken() + "/";
//                listTrigger.add(triggerDelete);
//            }
//        }else if(listTrigger.size() == 0){
//            Log.d(TAG,"3");
//            StringTokenizer st = new StringTokenizer(triggerName, "/");
//            for (int i = 0; i < st.countTokens() - 1; i++)
//                triggerDelete += st.nextToken() + "/";
//            triggerName = triggerDelete;
//
//        }else
//            Log.e(TAG,"저장된 값이 없어서 삭제 불가능합니다.");
//
//        for(int i = 0 ; i <listTrigger.size(); i++)
//            Log.d(TAG,i+"/"+listTrigger.get(i));
//    }

    public void addToTree(String triggerName){
        Log.d(MainActivity.TAG, "hi addToTree");
        Trees trees;
        trees = new Trees(triggerName);
        treeList.add(trees);
        treeListView.setAdapter(treeAdapter);
    }

}
