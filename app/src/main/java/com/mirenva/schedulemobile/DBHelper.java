package com.mirenva.schedulemobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", "--- onCreate database ---");

        db.execSQL("create table Schedule ("
                + "id integer primary key autoincrement,"
                + "room text,"
                + "day text,"
                + "groupNumber text,"
                + "hours text,"
                + "lecture text,"
                + "teacher text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
