package com.example.kotlinlesson2.model.retrofit

import com.example.kotlinlesson2.BuildConfig
import com.example.kotlinlesson2.domain.City
import com.example.kotlinlesson2.model.CommonWeatherCallback
import com.example.kotlinlesson2.model.RepositoryWeatherByCity
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.utils.bindDTOWithCity
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RepositoryLocationToOneWeatherRetrofitImpl: RepositoryWeatherByCity {

    val retrofitImpl = Retrofit.Builder()

    override fun getWeather(city: City, callback: CommonWeatherCallback) {

        retrofitImpl.baseUrl("https://api.weather.yandex.ru")
        retrofitImpl.addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        val api = retrofitImpl.build().create(WeatherAPI::class.java)

        api.getWeather(BuildConfig.WEATHER_API_KEY,city.lat,city.lon).enqueue(object : Callback<WeatherDTO> {
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {

                if(response.isSuccessful&&response.body()!=null){
                    callback.onResponse(bindDTOWithCity(response.body()!!,city))
                }else {
                    callback.onFailure(IOException("403 404"))
                }

            }
            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                callback.onFailure(t as IOException)
                //когда делаешь костыль, лучше обозначь его!!!
            }
        })
    }
}