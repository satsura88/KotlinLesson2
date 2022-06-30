package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather

interface Repository {
    fun getListWeather():List<Weather>
    fun getWeather(lat: Double, lon: Double):Weather
}