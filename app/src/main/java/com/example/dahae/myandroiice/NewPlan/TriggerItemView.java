package com.example.dahae.myandroiice.NewPlan;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class TriggerItemView extends LinearLayout {

    TextView TriggerTextView;

    public TriggerItemView(Context context) {
        super(context);
        init(context);
    }

    public TriggerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init (Context context){
        LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater2.inflate(R.layout.trigger_tree_item, this, true);

        TriggerTextView = (TextView) findViewById(R.id.triggerTreeItem);
    }

   public void setTriggerTextView(String wholePlanName) {
       TriggerTextView.setText(wholePlanName);
   }

}
