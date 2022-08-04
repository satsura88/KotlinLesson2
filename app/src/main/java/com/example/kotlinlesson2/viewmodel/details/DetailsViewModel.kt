package com.example.kotlinlesson2.viewmodel.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.WeatherApp
import com.example.kotlinlesson2.model.*
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.model.retrofit.RepositoryDetailsRetrofitImpl
import java.io.IOException

class DetailsViewModel(private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()) :
    ViewModel() {

    lateinit var repository: RepositoryWeatherByCity
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
                RepositoryDetailsRetrofitImpl()
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


    fun getWeather(lat: Double, lon: Double) {
        choiceRepository()
        liveData.value = DetailsFragmentAppState.Loading
        repository.getWeather(lat, lon, callback)
    }

    private val callback = object :LargeSuperCallback{
        override fun onResponse(weatherDTO: WeatherDTO) {
            liveData.postValue(DetailsFragmentAppState.Success(weatherDTO))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(DetailsFragmentAppState.Error(e))
        }
    }

    private fun isConnection(): Boolean {
        return false
    }

}