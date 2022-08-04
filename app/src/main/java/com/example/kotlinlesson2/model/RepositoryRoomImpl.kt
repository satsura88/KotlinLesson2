package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.WeatherApp
import com.example.kotlinlesson2.domain.City
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.room.WeatherEntity

class RepositoryRoomImpl:RepositoryWeatherByCity,RepositoryWeatherSave,RepositoryWeatherAvailable {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        callback.onResponse(WeatherApp.getWeatherDatabase().weatherDao().getWeatherByLocation(city.lat,city.lon).let{
            convertHistoryEntityToWeather(it).last()
        })
    }

    override fun addWeather(weather: Weather) {
        WeatherApp.getWeatherDatabase().weatherDao().insertRoom(convertWeatherToEntity(weather))
    }

    override fun getWeatherAll(callback: CommonListWeatherCallback) {
        callback.onResponse(convertHistoryEntityToWeather(WeatherApp.getWeatherDatabase().weatherDao().getWeatherAll()))
    }

    private fun convertHistoryEntityToWeather(entityList: List<WeatherEntity>): List<Weather> {
        return entityList.map {
            Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike)
        }
    }
    private fun convertWeatherToEntity(weather: Weather): WeatherEntity {
        return WeatherEntity(0, weather.city.name, weather.city.lat,weather.city.lon, weather.temperature, weather.feelsLike)
    }



}