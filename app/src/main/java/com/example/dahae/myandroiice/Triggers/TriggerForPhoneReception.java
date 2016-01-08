package com.example.dahae.myandroiice.Triggers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dahae.myandroiice.R;

public class TriggerForPhoneReception extends Activity {

    EditText mText;
    Button butSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_reception);

        mText = (EditText) findViewById(R.id.editText1);
        butSave = (Button) findViewById(R.id.buttonSave);

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mText.getText().toString();

                Intent intent = getIntent();
                intent.setAction("PhoneReception");
                intent.putExtra("mTriggerInfo", data);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}