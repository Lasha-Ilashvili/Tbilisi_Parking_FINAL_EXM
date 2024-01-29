package com.example.tbilisi_parking_final_exm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tbilisi_parking_final_exm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}