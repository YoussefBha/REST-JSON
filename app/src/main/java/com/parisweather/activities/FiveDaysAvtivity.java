package com.parisweather.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parisweather.R;
import com.parisweather.adapters.WeatherAdapter;
import com.parisweather.entities.Weather;
import com.parisweather.utils.Consts;
import com.parisweather.utils.RecyclerItemClickListener;
import com.parisweather.utils.Utility;
import com.parisweather.utils.WeatherBDD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by YoussefBha on 01/11/2016.
 */



//Five days activity
public class FiveDaysAvtivity extends AppCompatActivity {


    //Init

    WeatherBDD weatherBDD;

    private RecyclerView recyclerView;
    private TextView temp,description,speed,humidity,clouds,pressure,update,minmax;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout loading;
    private LinearLayout root;
    private Button tryagain;
    private ProgressBar progressBar;
    private ImageView logo,iconday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_days_avtivity);

        //Hide actionBar
        getSupportActionBar().hide();

        //Acces to database
        weatherBDD= new WeatherBDD(getApplicationContext());
        weatherBDD.open();

        //Init graphic
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        temp = (TextView) findViewById(R.id.temp);
        description = (TextView) findViewById(R.id.description);
        speed = (TextView) findViewById(R.id.speed);
        humidity = (TextView) findViewById(R.id.humidity);
        clouds = (TextView) findViewById(R.id.clouds);
        pressure = (TextView) findViewById(R.id.pressure);
        update = (TextView) findViewById(R.id.update);
        minmax = (TextView) findViewById(R.id.minmax);
        loading = (RelativeLayout) findViewById(R.id.loading);
        root = (LinearLayout) findViewById(R.id.root);
        tryagain = (Button) findViewById(R.id.tryagain);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        logo = (ImageView) findViewById(R.id.logo);
        iconday = (ImageView) findViewById(R.id.iconday);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        // Swiperefresh to update database with data from URL
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        new JSONParse().execute(Consts.ApiUrl);
                        //Update done
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        //AsyncTask execution to get data
        new JSONParse().execute(Consts.ApiUrl);


        //Connexion failed
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                tryagain.setVisibility(View.GONE);
                logo.setImageResource(R.drawable.cloud);
                new JSONParse().execute(Consts.ApiUrl);
            }
        });

    }



    //Show data
    private void SetupData(){

        //get current weather
        Weather weather = weatherBDD.getWeatherById(0);

        final WeatherAdapter weatherAdapter = new WeatherAdapter(this,weatherBDD.getWeathers());


        //SetUp data
        temp.setText(weather.getDay()+"°");
        description.setText(weather.getMain().substring(0,1).toUpperCase() + weather.getMain().substring(1) );
        speed.setText(weather.getSpeed()+"m/s");
        humidity.setText(weather.getHumidity()+"%");
        pressure.setText(weather.getPressure()+" hPa");
        clouds.setText(weather.getClouds()+"%");
        minmax.setText(weather.getMax()+"°/"+weather.getMin()+"°");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("date", "");
        Date date = new Date(name);
        String day = new SimpleDateFormat("HH:mm", Locale.FRENCH).format(date);
        update.setText("Derniére mise à jour : "+day);

        int ic = getApplicationContext().getResources().getIdentifier("icon"+weather.getIcon(),
                "drawable", getApplicationContext().getPackageName());
        iconday.setImageResource(ic);




        //Weather of five days
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(weatherAdapter);

        //Details weather of a day
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(FiveDaysAvtivity.this, OneDayActivity.class);
                        //Day id
                        i.putExtra("id",weatherAdapter.getList().get(position).getId());
                        startActivity(i);
                    }
                })
        );

        loading.setVisibility(View.GONE);
        root.setVisibility(View.VISIBLE);

    }


    //Connexion failed
    private void disconnected(){
        progressBar.setVisibility(View.GONE);
        tryagain.setVisibility(View.VISIBLE);
        logo.setImageResource(R.drawable.error);
    }

    //Back pressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(FiveDaysAvtivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Fermer ? ")
                .setMessage("Etes vous sur de vouloir quitter l'application?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Non", null)
                .show();
    }

    //AsyncTask to get data from url
    private class JSONParse extends AsyncTask<String, Void, String> {

        public JSONParse() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String str = "UNDEFINED";

            //Connexion test
            if(Utility.isNetworkAvailable(FiveDaysAvtivity.this)){

            try {
                //connexion à l'url
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                //recuperation des objets ou des tableau JSON

                JSONObject jsonobject = new JSONObject(builder.toString());
                JSONArray jsonarray = jsonobject.getJSONArray("list");

                //si la base de données local est vide ===> insertion
                if(weatherBDD.getWeathers().size() == 0){
                    for (int i = 0; i < jsonarray.length(); i++){
                        Weather weather = new Weather();
                        weather.setId(i);
                        JSONObject main = jsonarray.getJSONObject(i);
                        weather.setTime(main.get("dt").toString());
                        JSONObject temp= main.getJSONObject("temp");
                        weather.setDay(String.valueOf(temp.get("day")));
                        weather.setMin(String.valueOf(temp.get("min")));
                        weather.setMax(String.valueOf(temp.get("max")));
                        str = (String.valueOf(temp.get("max")));
                        weather.setNight(String.valueOf(temp.get("night")));
                        weather.setEve(String.valueOf(temp.get("eve")));
                        weather.setMor(String.valueOf(temp.get("night")));
                        weather.setNight(String.valueOf(temp.get("night")));
                        weather.setPressure(main.get("pressure").toString());
                        weather.setHumidity(main.get("humidity").toString());
                        weather.setSpeed(main.get("speed").toString());
                        weather.setDeg(main.get("deg").toString());
                        weather.setClouds(main.get("clouds").toString());
                        JSONArray weath= main.getJSONArray("weather");
                        JSONObject mainweather = weath.getJSONObject(0);
                        weather.setMain(String.valueOf(mainweather.get("main")));
                        weather.setDescription(String.valueOf(mainweather.get("description")));
                        weather.setIcon(String.valueOf(mainweather.get("icon")));
                        weatherBDD.insertTop(weather);
                    }
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("date",new Date().toString());
                    editor.apply();

                }
                //sinon ===> mise à jour
                else {
                    for (int i = 0; i < jsonarray.length(); i++){
                        Weather weather = new Weather();
                        weather.setId(i);
                        JSONObject main = jsonarray.getJSONObject(i);
                        weather.setTime(main.get("dt").toString());
                        JSONObject temp= main.getJSONObject("temp");
                        weather.setDay(String.valueOf(temp.get("day")));
                        weather.setMin(String.valueOf(temp.get("min")));
                        weather.setMax(String.valueOf(temp.get("max")));
                        weather.setNight(String.valueOf(temp.get("night")));
                        weather.setEve(String.valueOf(temp.get("eve")));
                        weather.setMor(String.valueOf(temp.get("night")));
                        weather.setNight(String.valueOf(temp.get("night")));
                        weather.setPressure(main.get("pressure").toString());
                        weather.setHumidity(main.get("humidity").toString());
                        weather.setSpeed(main.get("speed").toString());
                        weather.setDeg(main.get("deg").toString());
                        weather.setClouds(main.get("clouds").toString());
                        JSONArray weath= main.getJSONArray("weather");
                        JSONObject mainweather = weath.getJSONObject(0);
                        weather.setMain(String.valueOf(mainweather.get("main")));
                        weather.setDescription(String.valueOf(mainweather.get("description")));
                        weather.setIcon(String.valueOf(mainweather.get("icon")));
                        weatherBDD.Update(weather);
                    }
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("date",new Date().toString());
                    editor.apply();
                }

                urlConnection.disconnect();
            }
            // Probleme l'hors de la recuperation des données
            catch (IOException | JSONException e) {
                e.printStackTrace();
                disconnected();
            }

            } else {
                if (weatherBDD.getWeathers().size() == 0){
                    str = "failed";
                }
            }
            return str.trim();
        }

        //fontion d'après la tentative connexion
        @Override
        protected void onPostExecute(String temp) {
            //test des données recupérés
            if(temp.trim().equals("failed")){
                disconnected();
            } else {
                SetupData();
            }
        }
    }
}
