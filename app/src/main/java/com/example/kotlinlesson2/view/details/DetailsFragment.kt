package com.example.kotlinlesson2.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.kotlinlesson2.databinding.FragmentDetailsBinding
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.viewmodel.details.DetailsFragmentAppState
import com.example.kotlinlesson2.viewmodel.details.DetailsViewModel



class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() {
            return _binding!!
        }

    lateinit var weatherLocal: Weather

    private val viewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
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
            viewModel.getWeather(weatherLocal.city.lat,weatherLocal.city.lon)
            viewModel.getLiveData().observe(viewLifecycleOwner) {
                renderData(it)
            }
        }
    }

    private fun renderData(detailsFragmentAppState: DetailsFragmentAppState) {
        when(detailsFragmentAppState){
            is DetailsFragmentAppState.Error -> {}
            DetailsFragmentAppState.Loading -> {}
            is DetailsFragmentAppState.Success -> {
                with(binding) {
                    val weatherDTO = detailsFragmentAppState.weatherData
                    cityName.text = weatherLocal.city.name
                    temperatureValue.text = weatherDTO.fact.temp.toString()
                    feelsLikeValue.text = weatherDTO.fact.feelsLike.toString()
                    cityCoordinates.text = "${weatherLocal.city.lat}/${weatherLocal.city.lon}"
                    icon.loadUrl("https://yastatic.net/weather/i/icons/funky/dark/${weatherDTO.fact.icon}.svg")
                }
            }
        }
    }

    fun ImageView.loadUrl(url: String) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry{add(SvgDecoder(this@loadUrl.context))}
            .build()

        val request = coil.request.ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}