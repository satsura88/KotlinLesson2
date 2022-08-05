package com.example.kotlinlesson2.viewmodel.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.WeatherApp
import com.example.kotlinlesson2.domain.City
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.*
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.model.retrofit.RepositoryLocationToOneWeatherRetrofitImpl
import java.io.IOException

class DetailsViewModel(private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()) :
    ViewModel() {

    lateinit var repositoryLocationToOneWeather: RepositoryWeatherByCity
    lateinit var repositoryWeatherAddable: RepositoryWeatherSave

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        val sp = WeatherApp.getWeatherApp().getSharedPreferences("db_source", Context.MODE_PRIVATE)
        if (isConnection()) {
            repositoryLocationToOneWeather = when (2){
            1 -> {
                RepositoryOkHttpImpl()
            }
            2 -> {
                RepositoryLocationToOneWeatherRetrofitImpl()
            }
            3 -> {
                RepositoryWeatherLoaderImpl()
            }
            else -> {
                RepositoryRoomImpl()
            }
        }
            repositoryWeatherAddable = when (0) {
                1 -> {
                    RepositoryRoomImpl()
                }
                else -> {
                    RepositoryRoomImpl()
                }
            }
        } else {
            repositoryLocationToOneWeather = when (1) {
                1 -> {
                    RepositoryRoomImpl()
                }
                2 -> {
                    RepositoryLocalImpl()
                }
                else -> {
                    RepositoryLocalImpl()
                }
            }
            repositoryWeatherAddable = when (0) {
                1 -> {
                    RepositoryRoomImpl()
                }
                else -> {
                    RepositoryRoomImpl()
                }
            }
        }
    }


    fun getWeather(city: City) {
        liveData.value = DetailsFragmentAppState.Loading
        repositoryLocationToOneWeather.getWeather(city, callback)
    }

    private val callback = object :CommonWeatherCallback{
        override fun onResponse(weather: Weather) {
            liveData.postValue(DetailsFragmentAppState.Success(weather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(DetailsFragmentAppState.Error(e))
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

}