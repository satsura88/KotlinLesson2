package com.example.kotlinlesson2.view.details

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinlesson2.BuildConfig
import com.example.kotlinlesson2.domain.City
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.utils.*
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsServiceIntent: IntentService("") {

    override fun onHandleIntent(intent: Intent?) {

        intent?.let {
            it.getParcelableExtra<City>(BUNDLE_CITY_KEY)?.let {

                val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${it.lat}&lon=${it.lon}")

                Thread {
                    var myConnection: HttpsURLConnection? = null
                    myConnection = uri.openConnection() as HttpsURLConnection
                    try {
                        myConnection.readTimeout = 5000
                        myConnection.addRequestProperty(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)

                        val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                        val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)

                        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
                            putExtra(BUNDLE_WEATHER_DTO_KEY,weatherDTO)
                            action = WAVE
                        })
                    }catch (e:IOException){
                        //добавить обработку
                    }catch (e:JsonSyntaxException){
                        //добавить обработку
                    }catch (e:MalformedURLException){
                        //добавить обработку
                    }finally {
                        myConnection.disconnect()
                    }
                }.start()

            }

        }
    }
}