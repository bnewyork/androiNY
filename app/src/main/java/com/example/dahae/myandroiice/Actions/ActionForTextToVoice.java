package com.example.dahae.myandroiice.Actions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dahae.myandroiice.R;

import java.util.Locale;

public class ActionForTextToVoice extends Activity implements TextToSpeech.OnInitListener {

    EditText mText;
    Button butSave;
    Button butlisten;
    TextToSpeech mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicechange);

        mText = (EditText) findViewById(R.id.editText1);

        butlisten = (Button) findViewById(R.id.buttonListen);
        butSave = (Button) findViewById(R.id.buttonSave);

        mTts = new TextToSpeech(this, this);
        mTts.setSpeechRate(0.3f);
        butlisten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayIt();

            }
        });

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mText.getText().toString();
                Intent intent = getIntent();
                intent.setAction("TextToVoice");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                intent.putExtra("mActionInfo", data);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.KOREA);
           // if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
        }
    }

    private void sayIt() {
        String it = mText.getText().toString();
        mTts.speak(it, TextToSpeech.QUEUE_FLUSH, null);
    }
}
