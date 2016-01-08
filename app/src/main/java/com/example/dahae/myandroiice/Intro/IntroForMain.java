package com.example.dahae.myandroiice.Intro;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.dahae.myandroiice.R;

public class IntroForMain extends ActionBarActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    public Main1 main1;
    public Main2 main2;
    public Main3 main3;

    ToggleButton checkintro;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introformain);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);


        checkintro = (ToggleButton) findViewById(R.id.introcheck);
        close = (Button) findViewById(R.id.close);

        SharedPreferences pref = getSharedPreferences("pref",
                Activity.MODE_PRIVATE);
        Boolean chk = pref.getBoolean("IntroCheck", false);

        checkintro.setChecked(chk);

        if (checkintro.isChecked())
            checkintro.setBackgroundDrawable(getResources().getDrawable(
                 R.drawable.introcheck2));
        else
            checkintro.setBackgroundDrawable(getResources().getDrawable(
             R.drawable.introcheck1));

        checkintro.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (checkintro.isChecked()) {
                    checkintro.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.introcheck2));

                } else {
                    checkintro.setBackgroundDrawable(getResources().getDrawable(
                            R.drawable.introcheck1));


                }
            }
        });

        close.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("pref",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putBoolean("IntroCheck", checkintro.isChecked());
                editor.commit();
                IntroForMain.this.finish();
            }
        });
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                main1 = Main1.init(position);
                return main1;
            } else if(position == 1){
                main2 = Main2.init(position);
                return main2;
            }  else {
                main3 = Main3.init(position);
                return main3;
            }
        }

        @Override
        public int getCount() {
            return 3;  // 총 4개의 page 사용
        }

    }


}
