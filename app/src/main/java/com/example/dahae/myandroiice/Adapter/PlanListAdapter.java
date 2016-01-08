package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanListAdapter extends BaseAdapter{

    public ArrayList<PlanListItem> items  = new ArrayList<>();
    private Context context;

    public PlanListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(PlanListItem item){
        items.add(item);
    }

    public void deleteItem(int position){
        items.remove(position);
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

        PlanListItemView view = null;
        if(convertView == null){
            view = new PlanListItemView(context);
        } else {
            view = (PlanListItemView) convertView;
        }

        PlanListItem curItem = items.get(position);

        view.setKeyword(curItem.getKeyword());

        return view;
    }

}
