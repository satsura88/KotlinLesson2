package com.example.kotlinlesson2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlesson2.databinding.ActivityMainBinding
import com.example.kotlinlesson2.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.myRoot) // если не задан id то root


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }
    }
}