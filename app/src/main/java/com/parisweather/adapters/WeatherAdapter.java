package com.parisweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parisweather.R;
import com.parisweather.entities.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by YoussefBha on 01/11/2016.
 */

//class adapter de la liste de la meteo par jour
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {



    //declaration
    private Context mContext;
    private ArrayList<Weather> weathers;


    //Constructeur
    public WeatherAdapter(Context mContext, ArrayList<Weather> weathers) {
        this.mContext = mContext;
        this.weathers = weathers;
    }

    //Vue d'un seul jour
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day,max;
        public ImageView icon;


        public MyViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.day);
            max = (TextView) view.findViewById(R.id.max);
            icon = (ImageView) view.findViewById(R.id.icon);


        }
    }


    public ArrayList<Weather> getList(){
        return weathers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }

    //mise en place des données de chaque jours
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Weather weather = weathers.get(position);

        java.util.Date time = new java.util.Date(Long.parseLong(weather.getTime())*1000);
        String day = new SimpleDateFormat("EEEE", Locale.FRENCH).format(time);
        holder.day.setText(day.substring(0,1).toUpperCase()+day.substring(1));
        holder.max.setText(weather.getMax()+"°");
        int id = mContext.getResources().getIdentifier("icon"+weather.getIcon(),
                "drawable", mContext.getPackageName());
        holder.icon.setImageResource(id);

    }


}
