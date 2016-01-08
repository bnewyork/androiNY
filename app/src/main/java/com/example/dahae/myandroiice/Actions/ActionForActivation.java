package com.example.dahae.myandroiice.Actions;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dahae.myandroiice.Adapter.PlanItem;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.DBHelperForRecordTime;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;

public class ActionForActivation extends ActionBarActivity {

    ListView wholePlanListView;
    ExistingPlanAdapter planAdapter;
    ArrayList<PlanItem> planList = new ArrayList<PlanItem>();
    TextView textView;
    EditText PlanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        wholePlanListView = (ListView) findViewById(R.id.wholePlanList);
        textView = (TextView) findViewById(R.id.textView1);
        PlanName = (EditText) findViewById(R.id.editText2);

        displayListView();
    }

    public void onButton1Clicked(View v) {
        // 입력상자에 입력한 전화번호를 가져옴
        String planName = PlanName.getText().toString();

        Log.d(MainActivity.TAG, "planName " + planName);

        Intent intent = getIntent();
        intent.setAction("Activation");
        intent.putExtra("mActionInfo", planName);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void displayListView(){

        //메인List(보유 플랜 목록)한 뷰 띄우기
        planList.clear();

        PlanItem planItem;
        Cursor cursor = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);
        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String planName = cursor.getString(1);
                        String activationState = cursor.getString(2);
                        Log.d(MainActivity.TAG, "planName: " + planName + " / activationState: " + activationState);
                        if(activationState.equals("true")){
                            planItem = new PlanItem(planName, planName, true);
                        } else {
                            planItem = new PlanItem(planName, planName, false);
                        }
                        planList.add(planItem);
                    }
                }
            }
        } finally {
            cursor.close();
        }

        planAdapter = new ExistingPlanAdapter(this, R.layout.plans_item, planList);
        wholePlanListView.setAdapter(planAdapter);
    }

    private class ExistingPlanAdapter extends ArrayAdapter<PlanItem> {

        public ExistingPlanAdapter(Context context, int textViewResourceId, ArrayList<PlanItem> planList) {
            super(context, textViewResourceId, planList);
            planList = new ArrayList<PlanItem>();
            planList.addAll(planList);
        }

        private class ViewHolder {
            TextView name;
            CheckBox checkBox;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.plans_item, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.planItem);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_box);
                convertView.setTag(holder);

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        PlanItem planItem = (PlanItem) cb.getTag();
                        final String planName = planItem.getName();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + planName + " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();

                        if (cb.isChecked()) {
                            Log.d(MainActivity.TAG, "Clicked on Checkbox: " + planName + " is " + cb.isChecked());

                            MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable " +
                                    "SET activation = 'true' " +
                                    "WHERE planName = '" + planName + "';");

                        } else {
                            Log.d(MainActivity.TAG, "Clicked on Checkbox: " + planName + " is " + cb.isChecked());

                            MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable " +
                                    "SET activation = 'false' " +
                                    "WHERE planName = '" + planName + "';");

                        }

                        Cursor cursorI =  MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);
                        try {
                            for (int i = 0; i < cursorI.getCount(); i++) {
                                if (cursorI != null) {
                                    if (cursorI.moveToNext()) {
                                        int _idDB = cursorI.getInt(0);
                                        String planNameDBInfo = cursorI.getString(1);
                                        String activationDBInfo = cursorI.getString(2);

                                        Log.d(MainActivity.TAG, "ActivationInfoTable* _idDB : " + _idDB +
                                                " / planName : " + planNameDBInfo +
                                                " / activation : " + activationDBInfo);
                                    }
                                }
                                planItem.setSelected(cb.isChecked());
                            }
                        } finally {
                            if (cursorI != null)
                                cursorI.close();
                        }
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            PlanItem planItem = planList.get(position);
            holder.name.setText(planItem.getName());
            holder.checkBox.setChecked(planItem.isSelected());
            holder.checkBox.setTag(planItem);

            return convertView;
        }
    }

}