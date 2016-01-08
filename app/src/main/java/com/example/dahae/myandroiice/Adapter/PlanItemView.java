package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

import java.util.ArrayList;

public class PlanItemView extends LinearLayout {

    public TextView name;
    public CheckBox checkBox;

    public PlanItemView(Context context) {
        super(context);
        init(context);
    }

    public void init (Context context){

        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi.inflate(R.layout.plans_item, this, true);

        name = (TextView)findViewById(R.id.planItem);
        checkBox = (CheckBox) findViewById(R.id.cb_box);
    }
}
