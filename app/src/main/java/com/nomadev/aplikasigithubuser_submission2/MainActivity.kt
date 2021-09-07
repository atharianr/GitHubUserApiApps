package com.nomadev.aplikasigithubuser_submission2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nomadev.aplikasigithubuser_submission2.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val intentKeMain = Intent(this, SearchActivity::class.java)
            startActivity(intentKeMain)
            finish()
        }, 3000)
    }
}