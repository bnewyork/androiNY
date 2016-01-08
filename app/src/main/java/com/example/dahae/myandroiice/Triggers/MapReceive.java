package com.example.dahae.myandroiice.Triggers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.CheckPlan;

public class MapReceive extends BroadcastReceiver {

    private String mExpectedAction;
    private Intent mLastReceivedIntent;

    public MapReceive() {
    }

    public MapReceive(String expectedAction) {
        mExpectedAction = expectedAction;
        mLastReceivedIntent = null;
    }
    protected void onHandleIntent(Intent intent) {
    }

    public IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter(mExpectedAction);
        return filter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(MainActivity.TAG, "Location onReceive");

        if (intent != null) {
            mLastReceivedIntent = intent;

            int id = intent.getIntExtra("id", 0);
            double latitude = intent.getDoubleExtra("latitude", 0.0D);
            double longitude = intent.getDoubleExtra("longitude", 0.0D);

            Log.d(MainActivity.TAG, "Location : " + id + ", " + latitude + ", " + longitude);
            intentForService(context);
        }
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
    }
    public Intent getLastReceivedIntent() {
        return mLastReceivedIntent;
    }

    public void clearReceivedIntents() {
        mLastReceivedIntent = null;
    }

    public void intentForService(Context context){
        Log.d(MainActivity.TAG,"Location");

        Intent intent = new Intent(context, CheckPlan.class);
        intent.putExtra("BrodcastInfo", "Location");
        context.startService(intent);
    }
}
