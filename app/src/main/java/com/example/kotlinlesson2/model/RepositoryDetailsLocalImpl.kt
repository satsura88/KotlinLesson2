package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.domain.getBelarusCities
import com.example.kotlinlesson2.domain.getDefaultCity
import com.example.kotlinlesson2.domain.getWorldCities
import com.example.kotlinlesson2.model.dto.Fact
import com.example.kotlinlesson2.model.dto.WeatherDTO


class RepositoryDetailsLocalImpl:RepositoryDetails {

    override fun getWeather(lat: Double, lon: Double, callback: LargeSuperCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getBelarusCities())
        val response = list.filter { it.city.lat==lat&&it.city.lon==lon  }
        callback.onResponse(convertModelToDto(response.first()))
    }
    private  fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
        val fact: Fact = weatherDTO.fact
        return (Weather(getDefaultCity(), fact.temp, fact.feelsLike))
    }

    private fun convertModelToDto(weather:Weather): WeatherDTO{
        val fact: Fact = Fact(weather.feelsLike,weather.temperature)
        return WeatherDTO(fact)
    }
}