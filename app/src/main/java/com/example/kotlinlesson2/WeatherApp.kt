package com.example.kotlinlesson2

import android.app.Application

class WeatherApp:Application() {
    override fun onCreate() {
        super.onCreate()
        weatherApp = this
    }

    companion object{
        private var weatherApp:WeatherApp?=null
        fun getWeatherApp() = weatherApp!!
    }
}