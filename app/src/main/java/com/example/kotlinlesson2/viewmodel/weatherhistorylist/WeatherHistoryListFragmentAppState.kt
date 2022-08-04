package com.example.kotlinlesson2.viewmodel.weatherhistorylist

import com.example.kotlinlesson2.domain.Weather

sealed class WeatherHistoryListFragmentAppState {
    data class SuccessMulti(val weatherList: List<Weather>) : WeatherHistoryListFragmentAppState()
    data class Error(val error: Throwable) : WeatherHistoryListFragmentAppState()
    object Loading : WeatherHistoryListFragmentAppState()
}