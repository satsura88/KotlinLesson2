package com.example.kotlinlesson2.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(weatherEntity:WeatherEntity)

    @Query("INSERT INTO weather_entity_table (name,lat,lon,temperature,feelsLike) VALUES(:name,:lat,:lon,:temperature,:feelsLike)")
    fun insertNative1(name:String,lat:Double,lon:Double,temperature:Int,feelsLike:Int)

    @Query("INSERT INTO weather_entity_table (id,name,lat,lon,temperature,feelsLike) VALUES(:id,:name,:lat,:lon,:temperature,:feelsLike)")
    fun insertNative2(id:Long,name:String,lat:Double,lon:Double,temperature:Int,feelsLike:Int)

    @Query("SELECT * FROM weather_entity_table WHERE lat=:mLat AND lon=:mLon")
    fun getWeatherByLocation(mLat:Double,mLon:Double):List<WeatherEntity>

    @Query("SELECT * FROM weather_entity_table")
    fun getWeatherAll():List<WeatherEntity>

}