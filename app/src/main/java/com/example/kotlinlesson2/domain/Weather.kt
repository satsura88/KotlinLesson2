package com.example.kotlinlesson2.domain

data class Weather (
    val city: City = getDefaultCity(),
    val temperature: Int = 27,
    val feelsLike: Int = 28
)

data class City(
    val name: String,
    val lat:Double,
    val lon: Double
)

fun getDefaultCity() = City("Минск", 53.9000000, 27.5666700)