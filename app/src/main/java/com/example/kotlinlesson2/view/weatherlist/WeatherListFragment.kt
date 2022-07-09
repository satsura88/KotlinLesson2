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
import com.example.kotlinlesson2.viewmodel.AppState
import com.example.kotlinlesson2.viewmodel.WeatherListViewModel
import com.google.android.material.snackbar.Snackbar
import java.time.Duration
import java.util.concurrent.ArrayBlockingQueue


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

    lateinit var viewModel: WeatherListViewModel
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
        viewModel = ViewModelProvider(this).get(WeatherListViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<AppState> {
            override fun onChanged(t: AppState) {
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

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.showResult()
                binding.root.HW("Error", Snackbar.LENGTH_LONG, "Restart"){ _ ->
                    if (isBelarus) {
                        viewModel.getWeatherListForBelarus()
                    } else {
                        viewModel.getWeatherListForWorld()
                    }
                }
            }
            AppState.Loading -> {
                binding.loading()
            }
            is AppState.SuccessOne -> {
                binding.showResult()
                val result = appState.weatherData

            }
            is AppState.SuccessMulti -> {
                binding.showResult()
                binding.mainFragmentRecyclerView.adapter =
                    WeatherListAdapter(appState.weatherList, this)
            }
        }
    }

    fun View.HW(string: String, duration: Int, actionString: String, block:(v:View)->Unit){
        Snackbar.make(this, string, duration).setAction("Restart", block).show()
    }

     /*fun View.HW(string: String, duration: Int) {//
         Snackbar.make(this, string, duration).show()
     } */

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