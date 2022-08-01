package com.example.kotlinlesson2.model.retrofit

import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.utils.END_POINT
import com.example.kotlinlesson2.utils.WEATHER_API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {

    @GET(END_POINT)
    fun getWeather(
        @Header(WEATHER_API_KEY) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

    @GET(END_POINT)
    fun getWeather2(
        @Header(WEATHER_API_KEY) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

    @GET(END_POINT)
    fun getWeather3(
        @Header(WEATHER_API_KEY) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

    @GET(END_POINT)
    fun getWeather4(
        @Header(WEATHER_API_KEY) keyValue: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>
}