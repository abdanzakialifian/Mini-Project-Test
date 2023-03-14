package com.app.miniproject.presentation.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.miniproject.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}