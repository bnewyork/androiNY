package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class PlanListItemView extends LinearLayout {

    TextView keyword;

    public PlanListItemView(Context context) {
        super(context);
        init(context);
    }

    public PlanListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init (Context context){
        LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater2.inflate(R.layout.show_item, this, true);
        keyword = (TextView) findViewById(R.id.planItemShow);
    }

   public void setKeyword(String PlanName) {
       keyword.setText(PlanName);
   }

}
