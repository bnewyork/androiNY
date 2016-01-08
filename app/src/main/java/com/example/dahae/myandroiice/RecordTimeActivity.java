package com.example.dahae.myandroiice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.Records;
import com.example.dahae.myandroiice.MainFunction.DBHelperForRecordTime;

import java.util.ArrayList;
import java.util.List;

public class RecordTimeActivity extends AppCompatActivity {

    ArrayList<Records> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_time);

        displayRecordListView();
    }

    public void displayRecordListView(){

        recordList = new ArrayList<>();
        ListView RecordListView  = (ListView) findViewById(R.id.recordTimeListView);
        RecordAdapter recordAdapter = new RecordAdapter(this, R.layout.plan_record_item, recordList);

        Records records;
        Cursor cursorR = MainActivity.database.rawQuery("SELECT * FROM RecordTimeTable", null);

        try {
            if (MainActivity.database != null){
                if (cursorR != null && cursorR.getCount() != 0) {
                    while (cursorR.moveToNext()) {
                        String planName = cursorR.getString(1);
                        String time = cursorR.getString(2);

                        records = new Records(time, planName);
                        recordList.add(records);
                    }}}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursorR.close();
        }
        RecordListView.setAdapter(recordAdapter);
    }

    private class RecordAdapter extends ArrayAdapter<Records> {

        public RecordAdapter(Context context, int textViewResourceId, List<Records> recordList) {
            super(context, textViewResourceId, recordList);
            recordList = new ArrayList<>();
            recordList.addAll(recordList);
        }

        private class ViewHolder {
            TextView time;
            TextView name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.plan_record_item, null);

                holder = new ViewHolder();
                holder.time = (TextView) convertView.findViewById(R.id.recordTimeTextView);
                holder.name = (TextView) convertView.findViewById(R.id.recordNameTextView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Records records = recordList.get(recordList.size() - position - 1) ;
            holder.time.setText(records.getTime());
            holder.name.setText(records.getName());

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_time, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
