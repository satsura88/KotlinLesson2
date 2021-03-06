package com.example.kotlinlesson2.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinlesson2.databinding.FragmentDetailsBinding
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.model.dto.WeatherDTO
import com.example.kotlinlesson2.utils.BUNDLE_CITY_KEY
import com.example.kotlinlesson2.utils.BUNDLE_WEATHER_DTO_KEY
import com.example.kotlinlesson2.utils.WAVE

class DetailsFragment:Fragment() {

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

            LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                receiver,
                IntentFilter(WAVE)
            )

            requireActivity().startService(
                Intent(
                    requireContext(),
                    DetailsServiceIntent::class.java
                ).apply {
                    putExtra(BUNDLE_CITY_KEY, weatherLocal.city)
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

        with(binding){
            cityName.text = weather.city.name
            temperatureValue.text = weather.temperature.toString()
            feelsLikeValue.text = weather.feelsLike.toString()
            cityCoordinates.text = "${weather.city.lat}/${weather.city.lon}"
        }
    }

    companion object {
        const val BUNDLE_WEATHER_EXTRA = "???????? ???? ????????"
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