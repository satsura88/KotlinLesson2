package com.example.kotlinlesson2.viewmodel

sealed class AppState {
    data class Success(val weatherData: Any) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

class AppStateSecond:AppState(){

}