package com.example.dahae.myandroiice.Actions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class ActionForNotify extends AppCompatActivity {

    TextView textView1;
    EditText editText1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        textView1 = (TextView) findViewById(R.id.textView1);
        editText1 = (EditText) findViewById(R.id.editText1);
    }

    public void onButton1Clicked(View v) {

        String message = editText1.getText().toString();

        Intent intent = getIntent();
        intent.setAction("Notification");
        intent.putExtra("mActionInfo", message);
        setResult(RESULT_OK, intent);

        finish();
    }

}