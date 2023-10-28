package com.example.cashluckpatrol

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class MyApplication : Application() {
//    lateinit var scoreViewModel : ScoreViewModel
val scoreViewModel : ScoreViewModel by lazy {
    ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(ScoreViewModel::class.java)
}
    override fun onCreate() {
        super.onCreate()



    }

    }

