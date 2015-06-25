package com.mirenva.schedulemobile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    String room = "";
    String day = "";
    String group = "";
    String hours = "";
    String lecture = "";
    String teacher = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void editParameters(View v){
        Intent intent = new Intent(this, Parameters.class);
        startActivityForResult(intent, 1);
    }

    //TODO добавить приемник широкгоовещательно сообщения

}
