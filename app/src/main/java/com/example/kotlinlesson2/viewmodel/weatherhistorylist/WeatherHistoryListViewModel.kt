package com.example.kotlinlesson2.viewmodel.weatherhistorylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.IOException

class WeatherHistoryListViewModel(private val liveData: MutableLiveData<WeatherHistoryListFragmentAppState> = MutableLiveData<WeatherHistoryListFragmentAppState>()) :
    ViewModel() {

    lateinit var repository: RepositoryWeatherAvailable
    //lateinit var repositoryOne: RepositoryOne

    fun getLiveData(): MutableLiveData<WeatherHistoryListFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = RepositoryRoomImpl()
    }


    fun getAllHistory() {
        //choiceRepository()
        liveData.value = WeatherHistoryListFragmentAppState.Loading
        repository.getWeatherAll(callback)
    }

    private val callback = object : CommonListWeatherCallback {
        override fun onResponse(listWeather: List<Weather>) {
            liveData.postValue(WeatherHistoryListFragmentAppState.SuccessMulti(listWeather))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(WeatherHistoryListFragmentAppState.Error(e))
        }
    }
}