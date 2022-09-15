package com.iak.perstest.ui.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iak.perstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}