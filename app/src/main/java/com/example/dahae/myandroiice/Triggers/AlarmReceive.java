package com.example.dahae.myandroiice.Triggers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.CheckPlan;

public class AlarmReceive extends BroadcastReceiver {

    public static String ACTION_ALARM = "com.ctsd.activity.HomeContainer";

    public AlarmReceive() {
    }

    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        Bundle extra = intent.getExtras();
  //      String action = extra.getString(ACTION_ALARM);
        boolean[] week = extra.getBooleanArray("Week");
        boolean isRepeat = extra.getBoolean("Repeat");

    //    if (action.equals(ACTION_ALARM)) {
            if (extra != null) {
                if (isRepeat) {
                    for (int i = 1; i < week.length; i++) {
                        if (week[i]) {
                            Log.d(MainActivity.TAG, "***Alarm ; Repeat week[" + i + "] " + week[i]);
                            intentForService(context);
                        }
                    }
                } else {
                    Log.d(MainActivity.TAG, "***Alarm ; isOneTime");
                    intentForService(context);
                }
            }
     //   }
    }

    public void intentForService(Context context){
        Log.d(MainActivity.TAG,"***Alarm");
        Intent intent = new Intent(context, CheckPlan.class);
       // intent.putExtra("i", 0);
        //intent.putExtra("query", "empty");
       // intent.putExtra("Complex", "empty");
        intent.putExtra("BrodcastInfo", "Time");
        context.startService(intent);
    }
}
