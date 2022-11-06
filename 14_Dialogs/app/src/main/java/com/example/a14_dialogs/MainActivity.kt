package com.example.a14_dialogs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.a14_dialogs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.levelButton1.setOnClickListener {
            startActivity(Intent(this, DialogsLevel1Activity::class.java))
        }

        binding.levelButton2.setOnClickListener {
            startActivity(Intent(this, DialogsLevel2Activity::class.java))
        }

        binding.exitButton.setOnClickListener {
            finish()
        }
    }
}