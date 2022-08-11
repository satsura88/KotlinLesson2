package com.example.kotlinlesson2.view.weatherlist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinlesson2.R
import com.example.kotlinlesson2.databinding.FragmentWeatherListBinding
import com.example.kotlinlesson2.domain.Weather
import com.example.kotlinlesson2.utils.SP_DB_NAME_IS_BELARUS
import com.example.kotlinlesson2.utils.SP_KEY_IS_BELARUS
import com.example.kotlinlesson2.view.details.DetailsFragment
import com.example.kotlinlesson2.view.details.OnItemClick
import com.example.kotlinlesson2.viewmodel.citieslist.CityListFragmentAppState
import com.example.kotlinlesson2.viewmodel.citieslist.CitiesListViewModel
import java.security.AccessController.checkPermission


class CitiesListFragment : Fragment(), OnItemClick {

    companion object {
        fun newInstance() = CitiesListFragment()
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

        binding.weatherListFragmentFABCities.setOnClickListener {
            isBelarus = !isBelarus
            if (isBelarus) {
                viewModel.getWeatherListForBelarus()
                binding.weatherListFragmentFABCities.setImageResource(R.drawable.ic_belorus)
            } else {
                viewModel.getWeatherListForWorld()
                binding.weatherListFragmentFABCities.setImageResource(R.drawable.ic_earth)
            }
            val sp = requireActivity().getSharedPreferences(SP_DB_NAME_IS_BELARUS, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean(SP_KEY_IS_BELARUS,isBelarus)
            editor.commit()
            editor.apply()
        }
        viewModel.getWeatherListForBelarus()
        binding.weatherListFragmentFABLocation.setOnClickListener {
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0F,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            Log.d("logs", "${location.latitude} ${location.longitude}")
                        }
                    })
            }
        }
    }


    private val REQUEST_CODE_LOCATION = 999

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    private fun checkPermission(permission: String) {
        val permResult =
            ContextCompat.checkSelfPermission(requireContext(), permission)
        PackageManager.PERMISSION_GRANTED
        if (permResult == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к локации")
                .setMessage("ну вот нужно прям нужно!")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_LOCATION) {
            for (pIndex in permissions.indices) {
                if (permissions[pIndex] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[pIndex] == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
                    CitiesListAdapter(appState.weatherList, this)
            }
        }
    }

    fun FragmentWeatherListBinding.loading() {
        this.mainFragmentLoadingLayout.visibility = View.VISIBLE
        this.weatherListFragmentFABCities.visibility = View.GONE
    }

    fun FragmentWeatherListBinding.showResult() {
        this.mainFragmentLoadingLayout.visibility = View.GONE
        this.weatherListFragmentFABCities.visibility = View.VISIBLE
    }

    override fun onItemClick(weather: Weather) {
        requireActivity().supportFragmentManager.beginTransaction().hide(this).add(
            R.id.container, DetailsFragment.newInstance(weather)
        ).addToBackStack("").commit()
    }
}