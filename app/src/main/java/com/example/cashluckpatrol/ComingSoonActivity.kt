package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ComingSoonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)

        val name= intent.getStringExtra("name").toString()
    }
}