package com.example.githubapps.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapps.R
import com.example.githubapps.view.search.SearchUser
import kotlinx.android.synthetic.main.activity_splash.*

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashBar.visibility = View.VISIBLE
        Handler().postDelayed({
            startActivity(Intent(this, SearchUser::class.java))
            finish()
        },2000)
    }
}
