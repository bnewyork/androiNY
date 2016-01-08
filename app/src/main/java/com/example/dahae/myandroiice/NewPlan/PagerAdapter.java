package com.example.dahae.myandroiice.NewPlan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dahae.myandroiice.NewPlan.NewPlanAction;
import com.example.dahae.myandroiice.NewPlan.NewPlanConfig;
import com.example.dahae.myandroiice.NewPlan.NewPlanTrigger;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public NewPlanTrigger newPlanTrigger;
    public NewPlanAction newPlanAction;
    public NewPlanConfig newPlanConfig;

    public PagerAdapter(FragmentManager fm, NewPlanTrigger newPlanTrigger, NewPlanAction newPlanAction, NewPlanConfig newPlanConfig) {
        super(fm);

    }

    //해당하는 page의 fragment 생성
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            newPlanTrigger = NewPlanTrigger.init(position);
            return newPlanTrigger;
        } else if(position == 1){
            newPlanAction = NewPlanAction.init(position);
            return newPlanAction;
        } else {
            newPlanConfig = NewPlanConfig.init(position);
            return newPlanConfig;
        }
    }

    @Override
    public int getCount() {
        return 3;  // 총 3개의 page 사용
    }

}