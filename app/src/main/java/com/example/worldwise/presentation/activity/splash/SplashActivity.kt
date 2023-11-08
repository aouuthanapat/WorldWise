package com.example.worldwise.presentation.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.worldwise.R
import com.example.worldwise.presentation.activity.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
        }, 1000)
    }
}