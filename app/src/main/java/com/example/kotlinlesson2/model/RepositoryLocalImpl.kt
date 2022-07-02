package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.domain.getBelarusCities
import com.example.kotlinlesson2.domain.getWorldCities

class RepositoryLocalImpl:RepositoryMono,RepositoryMany {
    override fun getListWeather(location: Location): List<Weather> {
        return when(location){
            Location.Belarus -> {
                getBelarusCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}