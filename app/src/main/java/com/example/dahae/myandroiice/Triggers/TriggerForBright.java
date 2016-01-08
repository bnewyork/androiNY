package com.example.dahae.myandroiice.Triggers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;


public class TriggerForBright extends Activity {

    SeekBar SB;
    Button but;
    String str_bright;
    int bright;


    WindowManager.LayoutParams params;
    WindowManager.LayoutParams currnet_brightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bright);

        currnet_brightness= this.getWindow().getAttributes();

        Intent intent = getIntent();
        str_bright = intent.getStringExtra("Bright");

        but = (Button) findViewById(R.id.button);
        but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RetuningIntent();
            }
        });

        SB = (SeekBar) findViewById(R.id.seekBar);
        SB.setMax(100);
        SB.incrementProgressBy(5);
        SB.setProgress(Math.round(currnet_brightness.screenBrightness));

        Log.d(MainActivity.TAG, "currnet_brightness " + Math.round(currnet_brightness.screenBrightness));

        SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bright = progress;
                params = getWindow().getAttributes();
                params.screenBrightness = (float) progress / 100;
                getWindow().setAttributes(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    void RetuningIntent(){

        this.getWindow().setAttributes(currnet_brightness);

        Intent intent = getIntent();
        if(str_bright.equals("Up"))
            intent.setAction("BrightUp");
        else if(str_bright.equals("Down"))
            intent.setAction("BrightDown");
        Log.d(MainActivity.TAG, "brightness" + bright);
        intent.putExtra("mTriggerInfo", Integer.toString(bright));
        setResult(RESULT_OK, intent);

        finish();
    }
}
