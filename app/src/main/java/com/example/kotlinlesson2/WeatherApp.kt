package com.example.kotlinlesson2

import android.app.Application
import androidx.room.Room
import com.example.kotlinlesson2.model.room.WeatherDatabase
import com.example.kotlinlesson2.utils.ROOM_DB_NAME_WEATHER

class WeatherApp:Application() {
    override fun onCreate() {
        super.onCreate()
        weatherApp = this
    }

    companion object{
        private var weatherApp:WeatherApp?=null
        private var weatherDatabase: WeatherDatabase?=null
        fun getWeatherApp() = weatherApp!!
        fun getWeatherDatabase(){
            fun getWeatherDatabase(): WeatherDatabase {
                if (weatherDatabase == null) {
                    weatherDatabase = Room.databaseBuilder(
                        getWeatherApp(),
                        WeatherDatabase::class.java,
                        ROOM_DB_NAME_WEATHER
                    ).allowMainThreadQueries()
                        .build()
                }
                return weatherDatabase!!
            }
        }
    }
}