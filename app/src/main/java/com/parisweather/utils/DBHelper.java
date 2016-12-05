package com.parisweather.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YoussefBha on 01/11/2016.
 */


// SQLiteOpenHelper for the creation of the database

public class DBHelper extends SQLiteOpenHelper{

    //Sql query for table creation
    private static final String CREATE_WEATHER = "CREATE TABLE Weather(" +
            "id INTEGER," +
            "time TEXT," +
            "day TEXT," +
            "min TEXT," +
            "max TEXT," +
            "eve TEXT," +
            "mor TEXT," +
            "night TEXT," +
            "pressure TEXT," +
            "humidity TEXT," +
            "main TEXT," +
            "description TEXT," +
            "icon TEXT," +
            "speed TEXT," +
            "deg TEXT," +
            "clouds TEXT);";



    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // query execution
        db.execSQL(CREATE_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Weather;");
        onCreate(db);
    }

}
