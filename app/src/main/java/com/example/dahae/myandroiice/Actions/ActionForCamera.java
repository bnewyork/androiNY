package com.example.dahae.myandroiice.Actions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.dahae.myandroiice.R;

public class ActionForCamera extends Activity  {
    ActionForCameraSurface mSurface;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camare);

        mSurface = (ActionForCameraSurface)findViewById(R.id.preview);
        Timer timer = new Timer();
        TimerTask tt = new TimerTask()
        {
            @Override
            public void run(){
                startTakePicture ();
            }
        };
        timer.schedule(tt, 1500);

    }
    public void startTakePicture ()
    {
        mSurface.mCamera.autoFocus(mAutoFocus);
    }

    // actionInf포커싱 성공하면 촬영 허가
    AutoFocusCallback mAutoFocus = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            mSurface.mCamera.takePicture(null, null, mPicture);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 사진 저장.
    PictureCallback mPicture = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Calendar calendar = Calendar.getInstance();
            String FileName =
                    "/IceTest_"
                            + calendar.get(Calendar.YEAR) % 100 +"."+ (calendar.get(Calendar.MONTH)+1) +"."+calendar.get(Calendar.DAY_OF_MONTH) +"_"+ calendar.get(Calendar.HOUR_OF_DAY)
                            +"."+calendar.get(Calendar.MINUTE)+"."+ calendar.get(Calendar.SECOND)
                            +".jpg";

            String mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            String path = mRootPath + "/DCIM/Camera" + FileName;

            File file = new File(path);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                Toast.makeText(ActionForCamera.this, "Error T.T " +
                        e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.parse("file://" + path);
            intent.setData(uri);
            sendBroadcast(intent);

            Toast.makeText(ActionForCamera.this, "Save! : " + path, Toast.LENGTH_SHORT).show();
            //mSurface.mCamera.startPreview();
            //Intent intent1 = new Intent(this, MainActivity.class);

            finish();
        }
    };
}
