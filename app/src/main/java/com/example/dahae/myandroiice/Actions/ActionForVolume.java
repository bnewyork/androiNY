package com.example.dahae.myandroiice.Actions;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class ActionForVolume extends AppCompatActivity {

    SeekBar SB;
    Button but;
    int Volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        AudioManager manager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int currentVolumeMUSIC = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int currentVolumeRING = manager.getStreamVolume(AudioManager.STREAM_RING);
        but = (Button) findViewById(R.id.button);
        SB = (SeekBar) findViewById(R.id.seekBar);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        if(type.equals("Music")) {
            SB.setMax(15);
            SB.incrementProgressBy(1);
            SB.setProgress(currentVolumeMUSIC);

        } else if(type.equals("Ring")) {
            SB.setMax(7);
            SB.incrementProgressBy(1);
            SB.setProgress(currentVolumeRING);
        }

        SB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Volume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                intentForResult(Volume);
            }
        });
    }

    public void intentForResult(int progress) {

        String data = Integer.toString(progress);
        Intent intent = getIntent();
        intent.setAction("Volume");
        intent.putExtra("mActionInfo", data);
        setResult(RESULT_OK, intent);

        finish();
    }
}
