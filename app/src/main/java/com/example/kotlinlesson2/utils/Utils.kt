package com.example.kotlinlesson2.utils

import com.example.kotlinlesson2.domain.City
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.dto.Fact
import com.example.kotlinlesson2.model.dto.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

class Utils {
}

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

fun bindDTOWithCity(weatherDTO: WeatherDTO,city: City): Weather {
    val fact: Fact = weatherDTO.fact
    return (Weather(city, fact.temp, fact.feelsLike))
}

fun convertModelToDto(weather: Weather): WeatherDTO {
    val fact: Fact = Fact(weather.feelsLike, weather.temperature)
    return WeatherDTO(fact)
}