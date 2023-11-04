package com.example.cashluckpatrol

import android.app.Application
import androidx.lifecycle.ViewModelProvider

class MyApplication : Application() {
//    lateinit var scoreViewModel : ScoreViewModel
val scoreViewModel : ScoreViewModel by lazy {
    ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(ScoreViewModel::class.java)
}
    lateinit var soundHelper : SoundHelper
    override fun onCreate() {
        super.onCreate()
        soundHelper = SoundHelper(this)


    }

    }

