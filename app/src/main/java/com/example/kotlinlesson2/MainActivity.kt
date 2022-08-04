package com.example.kotlinlesson2

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlesson2.databinding.ActivityMainBinding
import com.example.kotlinlesson2.utils.SP_DB_NAME_IS_BELARUS
import com.example.kotlinlesson2.utils.SP_KEY_IS_BELARUS
import com.example.kotlinlesson2.view.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.myRoot)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherListFragment.newInstance()).commit()
        }

        val sp = getSharedPreferences(SP_DB_NAME_IS_BELARUS, Context.MODE_PRIVATE)
        val isRussian = sp.getBoolean(SP_KEY_IS_BELARUS,true)
        val editor = sp.edit()
        editor.putBoolean(SP_KEY_IS_BELARUS,isRussian)
        editor.apply()
        sp.edit().apply {
            putBoolean(SP_KEY_IS_BELARUS, isRussian)
            apply()
        }
    }

}