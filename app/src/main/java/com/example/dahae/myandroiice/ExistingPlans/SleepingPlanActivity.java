package com.example.dahae.myandroiice.ExistingPlans;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.PlanAdapter;
import com.example.dahae.myandroiice.Adapter.PlanItem;
import com.example.dahae.myandroiice.Adapter.PlanListAdapter;
import com.example.dahae.myandroiice.Adapter.PlanListItem;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.DBHelperForRecordTime;
import com.example.dahae.myandroiice.MainFunction.DBHelperForPlan;
import com.example.dahae.myandroiice.NewPlan.NewPlanCheck;
import com.example.dahae.myandroiice.NewPlan.NewPlanMaking;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class SleepingPlanActivity  extends AppCompatActivity {

    ListView subPlanListViewForTrigger;
    ListView subPlanListViewForAction ;
    ListView wholePlanListView;
    ArrayList<PlanItem> planList = new ArrayList<PlanItem>();

    PlanListAdapter subAdapterForTrigger = new PlanListAdapter(getApplication());
    PlanListAdapter subAdapterForAction = new PlanListAdapter(getApplication());

    TextView planName;

    NewPlanCheck newPlanCheck = new NewPlanCheck();
    ChangeName ChangeName = new  ChangeName();
    SubActivity subActivity = new SubActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholeplans);

        planName = (TextView) findViewById(R.id.nameText);
        subPlanListViewForTrigger = (ListView) findViewById(R.id.subListFortrigger);
        subPlanListViewForAction =(ListView) findViewById(R.id.subListForaction);
        wholePlanListView = (ListView) findViewById(R.id.wholePlanList);

        displayListView();

        wholePlanListView.setOnItemClickListener(mItemClickListener);
        wholePlanListView.setOnItemLongClickListener(mItemLongClickListener);
    }


    public void displayListView(){

        planList = subActivity.getdisplayListInFalse();
        PlanAdapter planAdapter = new PlanAdapter(this, planList);
        wholePlanListView.setAdapter(planAdapter);
    }

    private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            PlanItem planItem = (PlanItem) parent.getItemAtPosition(position);

            final String listViewName = planItem.getName();

            PopupMenu popup = new PopupMenu(SleepingPlanActivity.this, view);
            getMenuInflater().inflate(R.menu.menu_listview, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.modify:

                            Intent intentModify = new Intent(getApplicationContext(), ModifyPlanActivity.class);
                            intentModify.putExtra("listViewName", listViewName);
                            intentModify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intentModify, 0);
                            break;

                        case R.id.delete:

                            subActivity.deletePlan(listViewName);
                            displayListView();
                            planName.setText(null);
                            setSubAdapter(null); // sub 초기화
                            break;

                        case R.id.copy:

                            String oldName = listViewName;
                            String newName = listViewName.concat("_복사본");

                            if (newPlanCheck.NoSameName(newName)) {
                                subActivity.copyPlanAsFalse(oldName);
                                displayListView();
                                setSubAdapter(newName);
                                planName.setText(newName);
                            }
                            break;

                        case R.id.change:

                            AlertDialog.Builder alert = new AlertDialog.Builder(SleepingPlanActivity.this);

                            alert.setTitle("이름바꾸기");
                            alert.setMessage("새 이름을 입력해주세요");
                            final EditText input = new EditText(SleepingPlanActivity.this);
                            alert.setView(input);

                            alert.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String newName = input.getText().toString();

                                    if (newPlanCheck.NoSameName(newName)) {
                                        subActivity.changePlanName(listViewName, newName);
                                        displayListView();
                                    }
                                }
                            });

                            alert.setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) { }
                                    });

                            alert.show();
                            break;
                    }
                    return false;
                }
            });
            popup.show();
            return false;
        }
    };

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            PlanItem planItem = (PlanItem) parent.getItemAtPosition(position);
            final String listViewName = planItem.getName();
            setSubAdapter(listViewName);
            planName.setText(listViewName);
        }
    };

    public void setSubAdapter(String listViewName){

        subAdapterForAction = new PlanListAdapter(this);
        subAdapterForTrigger = new PlanListAdapter(this);

        if(listViewName != null) {
            Cursor cursorT = MainActivity.database.rawQuery("SELECT * FROM " + listViewName, null);
            Cursor cursorA = MainActivity.database.rawQuery("SELECT * FROM " + listViewName, null);
            try {
                if (MainActivity.database != null) {
                    if (cursorT != null && cursorT.getCount() != 0) {

                        while (cursorT.moveToNext()) {
                            String trigger = cursorT.getString(1);
                            if (trigger == null || trigger.equals("")) {
                            } else {
                                if (!trigger.equals("End") ){//&& !trigger.equals("Done")) {
                                    subAdapterForTrigger.addItem(new PlanListItem(ChangeName.Trigger(trigger)));//고쳐
                                }
                            }
                        }
                        cursorA.moveToLast();
                        String action = cursorA.getString(1);
                        StringTokenizer st = new StringTokenizer(action, "/");
                        while (st.hasMoreTokens()) {
                            subAdapterForAction.addItem(new PlanListItem(ChangeName.Action(st.nextToken())));//고쳐
                        }
                    }
                }
            } finally {
                cursorT.close();
                cursorA.close();
            }
        }
        subPlanListViewForTrigger.setAdapter(subAdapterForTrigger);
        subPlanListViewForAction.setAdapter(subAdapterForAction);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_active_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_new){    //액션바 "NEW"버튼 (새 플랜 만들기)
            Intent intentPlusButton = new Intent(getApplicationContext(), NewPlanMaking.class);
            startActivity(intentPlusButton);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}