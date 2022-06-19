package com.example.movieapps.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.movieapps.R
import com.example.movieapps.view.main.MainActivity
import maes.tech.intentanim.CustomIntent

class SplashScrennActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screnn)


        val mainLooperHandler = Handler(Looper.getMainLooper())

        mainLooperHandler.postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this@SplashScrennActivity, "fadein-to-fadeout")
            finish()
        }, 2000)

    }
}