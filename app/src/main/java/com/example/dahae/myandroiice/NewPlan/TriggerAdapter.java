package com.example.dahae.myandroiice.NewPlan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TriggerAdapter extends BaseAdapter{

    ArrayList<TriggerItem> items  = new ArrayList<>();

    private Context context;
    public TriggerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(TriggerItem item){
        items.add(item);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TriggerItemView view = null;
        if(convertView == null)
            view = new TriggerItemView(context);
        else
            view = (TriggerItemView) convertView;

        TriggerItem curItem = items.get(position);
        view.setTriggerTextView(curItem.getTriggerName());

        return view;
    }

}
