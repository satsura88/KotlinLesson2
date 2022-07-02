package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather

fun interface RepositoryMono {
    fun getWeather( lat: Double, lon: Double):Weather
}
fun interface RepositoryMany {
    fun getListWeather(location:Location):List<Weather>
}

sealed class Location{
    object Belarus:Location()
    object World:Location()
}