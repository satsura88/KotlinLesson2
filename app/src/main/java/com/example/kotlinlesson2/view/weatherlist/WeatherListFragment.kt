package com.example.kotlinlesson2.view.weatherlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinlesson2.R
import com.example.kotlinlesson2.databinding.FragmentWeatherListBinding
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.view.details.DetailsFragment
import com.example.kotlinlesson2.view.details.OnItemClick
import com.example.kotlinlesson2.viewmodel.citieslist.CityListFragmentAppState
import com.example.kotlinlesson2.viewmodel.citieslist.CitiesListViewModel


class WeatherListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = WeatherListFragment()
    }

    var isBelarus = true

    private var _binding: FragmentWeatherListBinding? = null
    private val binding: FragmentWeatherListBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    lateinit var viewModel: CitiesListViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CitiesListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<CityListFragmentAppState> {
            override fun onChanged(t: CityListFragmentAppState) {
                renderData(t)
            }
        })

        binding.weatherListFragmentFAB.setOnClickListener {
            isBelarus = !isBelarus
            if (isBelarus) {
                viewModel.getWeatherListForBelarus()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_belorus)
            } else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFAB.setImageResource(R.drawable.ic_earth)
            }
        }
        viewModel.getWeatherListForBelarus()
    }

    private fun renderData(appState: CityListFragmentAppState) {
        when (appState) {
            is CityListFragmentAppState.Error -> {
                binding.showResult()
            }
            CityListFragmentAppState.Loading -> {
                binding.loading()
            }
            is CityListFragmentAppState.SuccessOne -> {
                binding.showResult()
                val result = appState.weatherData

            }
            is CityListFragmentAppState.SuccessMulti -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherList, this)
            }
        }
    }

    fun FragmentWeatherListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFAB.visibility = View.GONE
    }

    fun FragmentWeatherListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFAB.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}