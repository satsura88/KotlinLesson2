package com.example.kotlinlesson2.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.model.*
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.model.retrofit.RepositoryDetailsRetrofitImpl
import java.io.IOException

class DetailsViewModel(private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()) :
    ViewModel() {

    lateinit var repository: RepositoryDetails

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = when (2) {
            1 -> {
                RepositoryDetailsOkHttpImpl()
            }
            2 -> {
                RepositoryDetailsRetrofitImpl()
            }
            3 -> {
                RepositoryDetailsWeatherLoaderImpl()
            }
            else -> {
                RepositoryDetailsLocalImpl()
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