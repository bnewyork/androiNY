package com.example.dahae.myandroiice.Triggers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class TriggerForBattery extends Activity {

    TextView textView;
    EditText editText_want;
    Button but, inC_but, deC_but;
    String str_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryRecv, filter);

        editText_want = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        inC_but = (Button) findViewById(R.id.buttonInc);
        deC_but = (Button) findViewById(R.id.buttonDec);


        editText_want.setText("50");

        but = (Button) findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               RetuningIntent();
           }
       });
        inC_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int data = Integer.parseInt(editText_want.getText().toString());
                editText_want.setText(Integer.toString(++data));
            }
        });

        deC_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int data = Integer.parseInt(editText_want.getText().toString());
                editText_want.setText(Integer.toString(--data));
            }
        });
    }
    BroadcastReceiver mBatteryRecv = new BroadcastReceiver() {
        // 이벤트를 수신하는 이벤트 함수
        public void onReceive(Context context, Intent intent) {
            // 배터리 잔량을 화면에 표시
            showLevel(intent);

        }
    };

    public void showLevel(Intent intent) {
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int percent = (int) ((float) level / (float) scale * 100.);
        textView.setText(Integer.toString(percent));
    }

    public void RetuningIntent(){
        Intent intent = getIntent();

        str_data = intent.getStringExtra("Battery");

        if(str_data.equals("Full")){
            intent.setAction("FullBattery");
        }else if(str_data.equals("Low")){
            intent.setAction("LowBattery");
        }

        String v = editText_want.getText().toString();
      //  Log.d(TAG, "D " + v);
        intent.putExtra("mTriggerInfo", v);
        setResult(RESULT_OK, intent);
        finish();
    }
}
