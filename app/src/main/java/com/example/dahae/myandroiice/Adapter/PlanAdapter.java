package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;

public class PlanAdapter extends BaseAdapter{

    public ArrayList<PlanItem> planList  = new ArrayList<>();
    private Context context;

    public PlanAdapter(Context context, ArrayList<PlanItem> localplanList) {
        this.planList = localplanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return  planList.size();
    }

    public void addItem(PlanItem item){
        planList.add(item);
    }

    public void deleteItem(int position){
        planList.remove(position);
    }

    @Override
    public Object getItem(int position) {
        return  planList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlanItemView view = null;

        if(convertView == null){
            view = new PlanItemView(context);
        } else {
            view = (PlanItemView) convertView;
        }

        PlanItem list = planList.get(position);
        view.name.setText(list.getName());
        view.checkBox.setChecked(list.isSelected());
        view.checkBox.setTag(list);

        view.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                PlanItem planItem = (PlanItem) cb.getTag();
                final String planName = planItem.getName();

                if (cb.isChecked()) {
                    MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable " +
                            "SET activation = 'true' " +
                            "WHERE planName = '" + planName + "';");
                } else {
                    MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable " +
                            "SET activation = 'false' " +
                            "WHERE planName = '" + planName + "';");
                }
            }
        });

        return view;
    }
}
