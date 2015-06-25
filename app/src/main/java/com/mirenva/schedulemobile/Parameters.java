package com.mirenva.schedulemobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;


public class Parameters extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
    }

    public void getSchedule(View v){
        Intent intentService = new Intent(this, GetterSchedule.class);
        Intent intentActivity = new Intent(this, MainActivity.class);

        EditText room = (EditText) findViewById(R.id.parameter_room);
        EditText day = (EditText) findViewById(R.id.parameter_day);
        EditText group = (EditText) findViewById(R.id.parameter_group);
        EditText hours = (EditText) findViewById(R.id.parameter_hours);
        EditText lecture = (EditText) findViewById(R.id.parameter_lecture);
        EditText teacher = (EditText) findViewById(R.id.parameter_teacher);

        intentService.putExtra("room", room.getText().toString());
        intentService.putExtra("day", day.getText().toString());
        intentService.putExtra("group", group.getText().toString());
        intentService.putExtra("hours", hours.getText().toString());
        intentService.putExtra("lecture", lecture.getText().toString());
        intentService.putExtra("teacher", teacher.getText().toString());

        startService(intentService);
        startActivity(intentActivity);
    }

}
