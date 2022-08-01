package com.example.kotlinlesson2.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinlesson2.BuildConfig
import com.example.kotlinlesson2.databinding.FragmentDetailsBinding
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.utils.BUNDLE_WEATHER_DTO_KEY
import com.example.kotlinlesson2.utils.WEATHER_API_KEY
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_WEATHER_DTO_KEY)
                    ?.let { weatherDTO ->
                        bindWeatherLocalWithWeatherDTO(weatherLocal, weatherDTO)
                    }
            }
        }
    }

    lateinit var weatherLocal: Weather

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weather = arguments?.let { arg ->
            arg.getParcelable<Weather>(BUNDLE_WEATHER_EXTRA)
        }

        weather?.let { weatherLocal ->
            this.weatherLocal = weatherLocal

            val client = OkHttpClient()
            val builder = Request.Builder()
            builder.addHeader(WEATHER_API_KEY, BuildConfig.WEATHER_API_KEY)
            builder.url("https://api.weather.yandex.ru/v2/informers?lat=${weatherLocal.city.lat}&lon=${weatherLocal.city.lon}")
            val request: Request = builder.build()
            val call: Call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code in 200..299) {
                        response.body?.let {
                            val responseString = it.string()
                            val weatherDTO =
                                Gson().fromJson((responseString), WeatherDTO::class.java)
                            weatherLocal.feelsLike = weatherDTO.fact.feelsLike
                            weatherLocal.temperature = weatherDTO.fact.temp
                            requireActivity().runOnUiThread {
                                renderData(weatherLocal)
                            }
                        }
                    }
                }
            })
        }
    }


    private fun bindWeatherLocalWithWeatherDTO(
        weatherLocal: Weather,
        weatherDTO: WeatherDTO
    ) {
        renderData(weatherLocal.apply {
            weatherLocal.feelsLike = weatherDTO.fact.feelsLike
            weatherLocal.temperature = weatherDTO.fact.temp
        })

    }

    private fun renderData(weather: Weather) {

        with(binding) {
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "пиво не пиво"
        fun newInstance(weather: Weather): DetailsFragment {

            val fr = DetailsFragment()

            fr.arguments = Bundle().apply {
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }

            fr.arguments = Bundle().also {
                it.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
                it.putParcelable(BUNDLE_WEATHER_EXTRA, weather)
            }

            return fr
        }
    }
}