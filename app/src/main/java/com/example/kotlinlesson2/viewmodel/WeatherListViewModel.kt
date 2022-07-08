package com.example.kotlinlesson2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.model.*
import kotlin.random.Random


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
        Thread{
            Thread.sleep(3000L)
            if((0..3).random(Random(System.currentTimeMillis()))==1){
                liveData.postValue(AppState.Error(IllegalStateException("WRONG")))
            }else{
                liveData.postValue(AppState.SuccessMulti(repositoryMulti.getListWeather(location)))
            }
        }.start()

    }

    private fun isConnection(): Boolean {
        return false
    }
}