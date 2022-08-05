package com.example.kotlinlesson2.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather (
    val city: City,
    var temperature: Int = 27,
    var feelsLike: Int = 28,
    var icon: String="ovc_-ra"
):Parcelable

@Parcelize
data class City(
    var name: String,
    val lat:Double,
    val lon: Double
):Parcelable

fun getWorldCities(): List<Weather> {
    return listOf(
        Weather(City("Лондон", 51.5085300, -0.1257400), 1, 2),
        Weather(City("Токио", 35.6895000, 139.6917100), 3, 4),
        Weather(City("Париж", 48.8534100, 2.3488000), 5, 6),
        Weather(City("Берлин", 52.52000659999999, 13.404953999999975), 7, 8),
        Weather(City("Рим", 41.9027835, 12.496365500000024), 9, 10),
        Weather(City("Москва", 55.755826, 37.617299900000035), 3, 3),
        Weather(City("Стамбул", 41.0082376, 28.97835889999999), 13, 14),
        Weather(City("Вашингтон", 38.9071923, -77.03687070000001), 15, 16),
        Weather(City("Киев", 50.4501, 30.523400000000038), 17, 18),
        Weather(City("Пекин", 39.90419989999999, 116.40739630000007), 19, 20)
    )
}

fun getBelarusCities(): List<Weather> {
    return listOf(
        Weather(City("Минск", 53.9000000, 27.5666700), 27, 28),
        Weather(City("Витебск", 55.1904000, 30.2049000), 27, 25),
        Weather(City("Гродно", 53.6884000, 23.8258000), 25, 28),
        Weather(City("Могилёв", 53.9168000, 30.3449000), 29, 27),
        Weather(City("Гомель", 52.4345000,  30.9754000), 31, 32),
        Weather(City("Брест", 52.0975500, 23.6877500), 30, 32),
    )
}

//fun getDefaultCity() = City("Минск", 53.9000000, 27.5666700)