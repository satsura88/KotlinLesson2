package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.dto.WeatherDTO
import java.io.IOException

fun interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double,callback: LargeSuperCallback)
}

interface LargeSuperCallback{
    fun onResponse(weatherDTO: WeatherDTO)
    fun onFailure(e: IOException)
}

fun interface RepositoryMono {
    fun getWeather( lat: Double, lon: Double):Weather
}

fun interface RepositoryCitiesList {
    fun getListWeather(location:Location):List<Weather>
}

sealed class Location{
    object Belarus:Location()
    object World:Location()
}