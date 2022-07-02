package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.domain.Weather


class RepositoryRemoteImpl:RepositoryMono {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread{
            Thread.sleep(300L)
        }.start()
        return Weather()
    }
}