package com.example.kotlinlesson2.viewmodel.citieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlesson2.model.*
import kotlin.random.Random


class CitiesListViewModel(private val liveData: MutableLiveData<CityListFragmentAppState> = MutableLiveData<CityListFragmentAppState>()):
    ViewModel(){

    private lateinit var repositoryCitiesList: RepositoryCitiesList

    fun getLiveData():MutableLiveData<CityListFragmentAppState>{
        choiceRepository()
        return liveData
    }

    private fun choiceRepository(){
       repositoryCitiesList = RepositoryCitiesListImpl()
    }

    fun getWeatherListForBelarus(){
        sentRequest(Location.Belarus)
    }
    fun getWeatherListForWorld(){
        sentRequest(Location.World)
    }

    private fun sentRequest(location: Location){
        liveData.value = CityListFragmentAppState.Loading
        Thread{
            Thread.sleep(300L)
            if((0..3).random(Random(System.currentTimeMillis()))==1){
                liveData.postValue(CityListFragmentAppState.Error(IllegalStateException("WRONG")))
            }else{
                liveData.postValue(CityListFragmentAppState.SuccessMulti(repositoryCitiesList.getListWeather(location)))
            }
        }.start()

    }

    private fun isConnection(): Boolean {
        return false
    }
}