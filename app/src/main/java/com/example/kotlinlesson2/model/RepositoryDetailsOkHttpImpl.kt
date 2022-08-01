package com.example.kotlinlesson2.model

import com.example.kotlinlesson2.BuildConfig
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.utils.WEATHER_API_KEY
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryDetailsOkHttpImpl:RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: LargeSuperCallback) {
        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.code in 200..299 && response.body != null) {
                    response.body?.let {
                        val responseString = it.string()
                        val weatherDTO =
                            Gson().fromJson((responseString), WeatherDTO::class.java)
                        callback.onResponse(weatherDTO)
                    }
                } else {
                    callback.onFailure(IOException("403 404"))
                }
            }
        })
    }
}