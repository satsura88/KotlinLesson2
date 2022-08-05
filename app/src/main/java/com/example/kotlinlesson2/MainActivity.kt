package com.example.kotlinlesson2

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlesson2.databinding.ActivityMainBinding
import com.example.kotlinlesson2.utils.SP_DB_NAME_IS_BELARUS
import com.example.kotlinlesson2.utils.SP_KEY_IS_BELARUS
import com.example.kotlinlesson2.view.room.WeatherHistoryListFragment
import com.example.kotlinlesson2.view.weatherlist.CitiesListFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.myRoot)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance()).commit()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, WeatherHistoryListFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}