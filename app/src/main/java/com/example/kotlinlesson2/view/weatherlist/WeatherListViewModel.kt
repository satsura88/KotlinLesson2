package com.example.kotlinlesson2.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.model.Repository
import com.example.kotlinlesson2.model.RepositoryLocalImpl
import com.example.kotlinlesson2.model.RepositoryRemoteImpl
import com.example.kotlinlesson2.viewmodel.AppState


class WeatherListViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()):
    ViewModel(){

    lateinit var repository: Repository

    fun getLiveData():MutableLiveData<AppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
        repository = if(isConnection()){
            RepositoryRemoteImpl()
        }else{
            RepositoryLocalImpl()
        }
    }

    fun sentRequest(){
        //choiceRepository() - при каждом запросе
        liveData.value = AppState.Loading
        if((0..3).random()==1){
            liveData.postValue(AppState.Error(throw IllegalStateException("WRONG")))
        }else{
            liveData.postValue(AppState.Success(repository.getWeather(53.9000000, 27.5666700)))
        }
    }

    private fun isConnection(): Boolean {
        return false
    }
}