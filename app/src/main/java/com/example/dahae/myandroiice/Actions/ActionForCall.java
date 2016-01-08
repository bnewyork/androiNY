package com.example.dahae.myandroiice.Actions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

public class ActionForCall extends AppCompatActivity {

    TextView textView1;
    EditText editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        textView1 = (TextView) findViewById(R.id.textView1);
        editText1 = (EditText) findViewById(R.id.editText1);


    }

    public void onButton1Clicked(View v) {
        // 입력상자에 입력한 전화번호를 가져옴
        String data = editText1.getText().toString();

        Log.d(MainActivity.TAG, "Call Num " + data);

        Intent intent = getIntent();
        intent.setAction("Call");
        intent.putExtra("mActionInfo", data);
        setResult(RESULT_OK, intent);

        finish();
    }




}
