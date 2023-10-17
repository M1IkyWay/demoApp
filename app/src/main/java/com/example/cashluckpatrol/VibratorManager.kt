package com.example.cashluckpatrol

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService

class VibratorManager (private val context : Context) {


    private val myVibrator: Vibrator? = try {
        context.getSystemService(Vibrator::class.java)
    } catch (e: Exception) {
        null
    }


}