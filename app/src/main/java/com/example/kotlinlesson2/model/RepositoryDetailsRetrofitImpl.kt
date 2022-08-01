package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.domain.getDefaultCity

class RepositoryDetailsRetrofitImpl:RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: LargeSuperCallback) {
    }
}