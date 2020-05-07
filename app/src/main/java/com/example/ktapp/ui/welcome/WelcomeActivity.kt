package com.example.ktapp.ui.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ktapp.R

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WelcomeFragment.newInstance())
                .commitNow()
        }
    }

}
