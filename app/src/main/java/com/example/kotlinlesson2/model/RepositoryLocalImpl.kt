package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.*



class RepositoryLocalImpl:RepositoryWeatherByCity {

    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getBelarusCities())
        val response = list.filter { it.city.lat==city.lat&&it.city.lon==city.lon  }
        callback.onResponse((response.first()))
    }
}