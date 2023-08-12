package io.jadu.kibo.ui.features.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import io.jadu.kibo.MainActivity
import io.jadu.kibo.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this ,MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}