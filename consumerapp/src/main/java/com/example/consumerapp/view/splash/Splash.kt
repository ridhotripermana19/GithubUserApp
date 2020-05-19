package com.example.consumerapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.consumerapp.R
import com.example.consumerapp.view.favorite.Favorite
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.title = "Consumer Favorites"

        splashBar.visibility = View.VISIBLE
        Handler().postDelayed({
            startActivity(Intent(this, Favorite::class.java))
            finish()
        },2000)
    }
}
