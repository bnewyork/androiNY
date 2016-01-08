package com.example.dahae.myandroiice.Actions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;

public class ActionForScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        Bundle extra = intent.getExtras();

        boolean isScreen;
        isScreen = extra.getBoolean("isScreen");
        Action(isScreen);
    }

    void Action( boolean isScreen){

        if(!isScreen){

            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
            getWindow().setAttributes(params);
        }
        finish();
    }
}
