package com.example.kotlinlesson2.utils

import com.example.kotlinlesson2.BuildConfig
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.view.details.OnResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object WeatherLoader {

    fun requestToYa(lat: Double,lon: Double,onResponse: OnResponse){
        val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
        Thread{
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            onResponse.onResponse(weatherDTO)
        }.start()
    }
}