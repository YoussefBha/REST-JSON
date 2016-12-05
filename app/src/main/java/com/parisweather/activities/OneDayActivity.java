package com.parisweather.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parisweather.R;
import com.parisweather.entities.Weather;
import com.parisweather.utils.WeatherBDD;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by YoussefBha on 01/11/2016.
 */


//Details activity
public class OneDayActivity extends AppCompatActivity {

    //Declaration
    private TextView temp,description,speed,humidity,clouds,pressure,update,minmax,mor,eve,night,day,main;
    private ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day);

        //Mise en place de l'actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        //Recuperation de l'id du jour depuis la vue principale
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        //Définion des elements graphiques
        temp = (TextView) findViewById(R.id.temp);
        description = (TextView) findViewById(R.id.description);
        speed = (TextView) findViewById(R.id.speed);
        humidity = (TextView) findViewById(R.id.humidity);
        clouds = (TextView) findViewById(R.id.clouds);
        pressure = (TextView) findViewById(R.id.pressure);
        update = (TextView) findViewById(R.id.update);
        minmax = (TextView) findViewById(R.id.minmax);
        mor = (TextView) findViewById(R.id.mor);
        eve = (TextView) findViewById(R.id.eve);
        night = (TextView) findViewById(R.id.night);
        day = (TextView) findViewById(R.id.day);
        main = (TextView) findViewById(R.id.main);
        icon = (ImageView) findViewById(R.id.iconday);






        //Acces à la base de données
        WeatherBDD weatherBDD = new WeatherBDD(getApplicationContext());
        weatherBDD.open();
        Weather weather = weatherBDD.getWeatherById(id);


        //mise en place des données

        java.util.Date time = new java.util.Date(Long.parseLong(weather.getTime())*1000);
        String d = new SimpleDateFormat("EEEE", Locale.FRENCH).format(time);
        getSupportActionBar().setTitle(d.substring(0,1).toUpperCase() + d.substring(1));
        temp.setText(weather.getDay()+"°");
        mor.setText(weather.getMor()+"°");
        eve.setText(weather.getEve()+"°");
        night.setText(weather.getNight()+"°");
        day.setText(weather.getDay()+"°");
        description.setText(weather.getMain().substring(0,1).toUpperCase() + weather.getMain().substring(1) );
        speed.setText(weather.getSpeed()+"m/s");
        humidity.setText(weather.getHumidity()+"%");
        pressure.setText(weather.getPressure()+" hPa");
        clouds.setText(weather.getClouds()+"%");
        minmax.setText(weather.getMax()+"°/"+weather.getMin()+"°");
        main.setText(weather.getDescription());
        int ic = getApplicationContext().getResources().getIdentifier("icon"+weather.getIcon(),
                "drawable", getApplicationContext().getPackageName());
        icon.setImageResource(ic);




        weatherBDD.close();
    }


    //fontion retour
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
