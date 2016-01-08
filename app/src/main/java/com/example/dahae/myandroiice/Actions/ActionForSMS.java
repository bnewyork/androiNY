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

public class ActionForSMS  extends AppCompatActivity {

    TextView textView1;
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        textView1 = (TextView) findViewById(R.id.textView1);

        //number
        editText1 = (EditText) findViewById(R.id.editText1);

        //message
        editText2 = (EditText) findViewById(R.id.editText2);

    }

    public void onButton1Clicked(View v) {
        // 입력상자에 입력한 전화번호를 가져옴
        String num = editText1.getText().toString();
        String message = editText2.getText().toString();

        Log.d("[ANDROI-ICE]", "message "  +message);

        Intent intent = getIntent();
        intent.setAction("SMS");
        intent.putExtra("mActionInfo", message+"+"+num);
        setResult(RESULT_OK, intent);

        finish();
    }

}
