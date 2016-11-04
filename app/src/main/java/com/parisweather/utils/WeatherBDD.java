package com.parisweather.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parisweather.entities.Weather;
import java.util.ArrayList;

/**
 * Created by YoussefBha on 01/11/2016.
 */

//Classe de la gestion entre la base de données pour la classe meteo du jour
public class WeatherBDD {



    private SQLiteDatabase bdd;

    private DBHelper dbHelper;

    public WeatherBDD(Context context) {
        super();
        dbHelper = new DBHelper(context, "weather.db", null, 1);
    }

    public void open() {
        bdd = dbHelper.getWritableDatabase();
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBDD() {
        return bdd;
    }

    //Fontion insertion
    public long insertTop(Weather weather) {


        ContentValues values = new ContentValues();
        values.put("id", weather.getId());
        values.put("time", weather.getTime());
        values.put("day", weather.getDay());
        values.put("min", weather.getMin());
        values.put("max", weather.getMax());
        values.put("eve", weather.getEve());
        values.put("mor", weather.getMor());
        values.put("night", weather.getNight());
        values.put("pressure", weather.getPressure());
        values.put("humidity", weather.getHumidity());
        values.put("main", weather.getMain());
        values.put("description", weather.getDescription());
        values.put("icon", weather.getIcon());
        values.put("speed", weather.getSpeed());
        values.put("deg", weather.getDeg());
        values.put("clouds", weather.getClouds());
        return bdd.insert("Weather", null, values);
    }


    //Fontion mise à jour
    public long Update(Weather weather) {
        ContentValues values = new ContentValues();
        values.put("id", weather.getId());
        values.put("time", weather.getTime());
        values.put("day", weather.getDay());
        values.put("min", weather.getMin());
        values.put("max", weather.getMax());
        values.put("eve", weather.getEve());
        values.put("mor", weather.getMor());
        values.put("night", weather.getNight());
        values.put("pressure", weather.getPressure());
        values.put("humidity", weather.getHumidity());
        values.put("main", weather.getMain());
        values.put("description", weather.getDescription());
        values.put("icon", weather.getIcon());
        values.put("speed", weather.getSpeed());
        values.put("deg", weather.getDeg());
        values.put("clouds", weather.getClouds());
        return bdd.update("Weather", values, "id="+weather.getId(),null);
    }



    //Fontion de recuperation de la meteo de tout les jours
    public ArrayList<Weather> getWeathers(){

        ArrayList<Weather> list = new ArrayList<Weather>();
        Cursor cursor = this.bdd.query("Weather", new String[] { "*" },
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Weather weather = new Weather();
                weather.setId(cursor.getInt(0));
                weather.setTime(cursor.getString(1));
                weather.setDay(cursor.getString(2));
                weather.setMin(cursor.getString(3));
                weather.setMax(cursor.getString(4));
                weather.setEve(cursor.getString(5));
                weather.setMor(cursor.getString(6));
                weather.setNight(cursor.getString(7));
                weather.setPressure(cursor.getString(8));
                weather.setHumidity(cursor.getString(9));
                weather.setMain(cursor.getString(10));
                weather.setDescription(cursor.getString(11));
                weather.setIcon(cursor.getString(12));
                weather.setSpeed(cursor.getString(13));
                weather.setDeg(cursor.getString(14));
                weather.setClouds(cursor.getString(15));
                list.add(weather);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }



    //Fontion de recuperation de la meteo du jour par id
    public Weather getWeatherById(int id){

        Weather weather = new Weather();
        Cursor cursor = this.bdd.rawQuery("SELECT * FROM Weather WHERE id ='"+id+"'", null);
        if (cursor.moveToFirst()) {
            do {

                weather.setId(cursor.getInt(0));
                weather.setTime(cursor.getString(1));
                weather.setDay(cursor.getString(2));
                weather.setMin(cursor.getString(3));
                weather.setMax(cursor.getString(4));
                weather.setEve(cursor.getString(5));
                weather.setMor(cursor.getString(6));
                weather.setNight(cursor.getString(7));
                weather.setPressure(cursor.getString(8));
                weather.setHumidity(cursor.getString(9));
                weather.setMain(cursor.getString(10));
                weather.setDescription(cursor.getString(11));
                weather.setIcon(cursor.getString(12));
                weather.setSpeed(cursor.getString(13));
                weather.setDeg(cursor.getString(14));
                weather.setClouds(cursor.getString(15));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return weather;
    }

}
