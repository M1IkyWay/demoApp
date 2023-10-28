package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

class SlotGame2Activity : AppCompatActivity() {

    private val userViewModel by lazy { ViewModelProviders.of(this).get(ScoreViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slot_game2)
    }
}