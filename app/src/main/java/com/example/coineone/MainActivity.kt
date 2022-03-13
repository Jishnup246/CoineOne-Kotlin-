package com.example.coineone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circularProgressBar = findViewById<View>(R.id.circularProgress) as CircularProgressBar
        circularProgressBar.setProgressValue(60, 60, 60)
    }
}