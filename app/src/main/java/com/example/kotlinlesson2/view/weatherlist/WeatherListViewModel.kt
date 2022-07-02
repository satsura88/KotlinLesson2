package com.example.kotlinlesson2.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.model.*
import com.example.kotlinlesson2.viewmodel.AppState


class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()):
    ViewModel(){

    lateinit var repositoryOne: RepositoryMono
    lateinit var repositoryMulti: RepositoryMany

    fun getLiveData():MutableLiveData<AppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
        repositoryOne = if(isConnection()){
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
        repositoryMulti = RepositoryLocalImpl()
    }

    fun getWeatherListForBelarus(){
        sentRequest(Location.Belarus)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location){
        //choiceRepository() - при каждом запросе
        liveData.value = AppState.Loading
        if(false){
            liveData.postValue(AppState.Error(throw IllegalStateException("WRONG")))
        }else{
            liveData.postValue(AppState.SuccessMulti(repositoryMulti.getListWeather(location)))
        }
    }

    private fun isConnection(): Boolean {
        return false
    }
}