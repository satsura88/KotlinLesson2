package com.example.kotlinlesson2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinlesson2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.parent) // если не задан id то root
        //setContentView(R.layout.activity_main)
        binding.btn.text = "Change"
        //findViewById<Button>(R.id.btn).text = "Change"
    }
}