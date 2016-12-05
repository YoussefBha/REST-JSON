package com.parisweather.entities;

/**
 * Created by YoussefBha on 01/11/2016.
 */


//Class weather
public class Weather {


    public Weather(int id, String time, String day, String min, String max, String eve, String mor, String night, String pressure, String humidity, String main, String description, String icon, String speed, String deg, String clouds) {
        this.id = id;
        this.time = time;
        this.day = day;
        this.min = min;
        this.max = max;
        this.eve = eve;
        this.mor = mor;
        this.night = night;
        this.pressure = pressure;
        this.humidity = humidity;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
    }

    private int id;
    private String time;
    private String day;
    private String min;
    private String max;
    private String eve;
    private String mor;
    private String night;
    private String pressure;
    private String humidity;
    private String main;
    private String description;
    private String icon;
    private String speed;
    private String deg;
    private String clouds;

    public Weather() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public String getMor() {
        return mor;
    }

    public void setMor(String mor) {
        this.mor = mor;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }
}
