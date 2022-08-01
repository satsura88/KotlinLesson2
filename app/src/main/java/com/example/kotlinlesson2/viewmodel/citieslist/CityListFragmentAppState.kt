package com.example.kotlinlesson2.viewmodel.citieslist

import com.example.kotlinlesson2.domain.Weather

sealed class CityListFragmentAppState {
    data class SuccessOne(val weatherData: Weather) : CityListFragmentAppState()
    data class SuccessMulti(val weatherList: List<Weather>) : CityListFragmentAppState()
    data class Error(val error: Throwable) : CityListFragmentAppState()
    object Loading : CityListFragmentAppState()
}
