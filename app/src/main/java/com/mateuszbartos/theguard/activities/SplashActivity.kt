package com.mateuszbartos.theguard.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)

        finish()
    }
}