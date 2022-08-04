package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.City
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.dto.WeatherDTO
import java.io.IOException

fun interface RepositoryWeatherByCity {
    fun getWeather(city: City,callback: CommonWeatherCallback)
}

fun interface RepositoryWeatherAvailable {
    fun getWeatherAll(callback: CommonListWeatherCallback)
}

fun interface RepositoryWeatherSave {
    fun addWeather(weather: Weather)
}

interface CommonWeatherCallback{
    fun onResponse(weather: Weather)
    fun onFailure(e: IOException)
}

interface CommonListWeatherCallback{
    fun onResponse(weather: List<Weather>)
    fun onFailure(e: IOException)
}

fun interface RepositoryCitiesList {
    fun getListWeather(location:Location):List<Weather>
}

sealed class Location{
    object Belarus:Location()
    object World:Location()
}