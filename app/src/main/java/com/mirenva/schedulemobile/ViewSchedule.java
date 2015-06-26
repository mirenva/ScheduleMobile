package com.mirenva.schedulemobile;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewSchedule extends Activity {

    BroadcastReceiver br;


    String room = "";
    String day = "";
    String group = "";
    String hours = "";
    String lecture = "";
    String teacher = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);



        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {

                ArrayList<Map<String, Object>> schedule = new ArrayList<>();

                DBHelper dbHelper = new DBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor c = db.query("schedule", null, null, null, null, null, null);

                if (c.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                    int room = c.getColumnIndex("room");
                    int day = c.getColumnIndex("day");
                    int group = c.getColumnIndex("groupNumber");
                    int hours = c.getColumnIndex("hours");
                    int lecture = c.getColumnIndex("lecture");
                    int teacher = c.getColumnIndex("teacher");

                    do {
                        Map<String, Object> m = new HashMap<>();

                        m.put("room", c.getString(room));
                        m.put("day", c.getString(day));
                        m.put("group", c.getString(group));
                        m.put("hours", c.getString(hours));
                        m.put("lecture", c.getString(lecture));
                        m.put("teacher", c.getString(teacher));

                        schedule.add(m);

                    } while (c.moveToNext());
                }

                c.close();

                String[] from = {"room", "day", "group", "hours", "lecture", "teacher"};
                int[] to = { R.id.room, R.id.day, R.id.group, R.id.hours, R.id.lecture, R.id.teacher};

                SimpleAdapter sAdapter = new SimpleAdapter(context, schedule, R.layout.list_item, from, to);

                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(sAdapter);

                if (schedule.size() == 0){
                    TextView noData = (TextView) findViewById(R.id.no_data);
                    noData.setVisibility(View.VISIBLE);

                }

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
            }
        };

        IntentFilter intentFilter = new IntentFilter("EndOperation");
        registerReceiver(br, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(br);
    }



}
