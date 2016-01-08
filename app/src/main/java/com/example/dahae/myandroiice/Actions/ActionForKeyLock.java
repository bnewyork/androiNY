package com.example.dahae.myandroiice.Actions;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.dahae.myandroiice.R;

public class ActionForKeyLock extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                // 키잠금 해제하기
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

}
