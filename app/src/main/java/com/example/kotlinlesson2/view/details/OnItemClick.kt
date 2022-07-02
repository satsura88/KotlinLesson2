package com.example.kotlinlesson2.view.details

import com.example.kotlinlesson2.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}