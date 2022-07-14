package com.example.kotlinlesson2.view.details

import com.example.kotlinlesson2.model.dto.WeatherDTO

fun interface OnResponse {
    fun onResponse(weather: WeatherDTO)
}