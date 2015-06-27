package com.mirenva.schedulemobile;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public class GetterSchedule extends IntentService {


    public GetterSchedule() {
        super("GetterSchedule");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        Map<String, String> parameters = new HashMap<>();

        parameters.put("room",    intent.getStringExtra("room"));
        parameters.put("day",     intent.getStringExtra("day"));
        parameters.put("groupNumber",   intent.getStringExtra("group"));
        parameters.put("hours",   intent.getStringExtra("hours"));
        parameters.put("lecture", intent.getStringExtra("lecture"));
        parameters.put("teacher", intent.getStringExtra("teacher"));

        new ScheduleDownloader(parameters).start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ScheduleDownloader extends Thread{

        Map<String, String> parameters;

        public ScheduleDownloader(Map<String, String> parameters){
            this.parameters = parameters;
        }

        @Override
        public void run() {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://shedule.herokuapp.com")
                    .build();
            ScheduleInterface scheduleInterface = restAdapter.create(ScheduleInterface.class);
            Schedule schedule = scheduleInterface.getSchedule(parameters);

            ContentValues cv = new ContentValues();
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.delete("schedule", null, null);

            for (Schedule.Objects elem : schedule.objects) {
                cv.put("room",        elem.room);
                cv.put("day",         elem.day);
                cv.put("groupNumber", elem.groupNumber);
                cv.put("hours",       elem.hours);
                cv.put("lecture",     elem.lecture);
                cv.put("teacher",     elem.teacher);

                db.insert("schedule", null, cv);
            }

            dbHelper.close();

            Intent intent = new Intent("EndOperation");
            sendBroadcast(intent);
        }

    }

    private interface ScheduleInterface {
        @GET("/api/findLessons")
        Schedule getSchedule(@QueryMap Map<String, String> parameters);

    }

    private class Schedule {
        Objects objects[];
        private class Objects {
            String room;
            String day;
            String groupNumber;
            String hours;
            String lecture;
            String teacher;
        }
    }
}
