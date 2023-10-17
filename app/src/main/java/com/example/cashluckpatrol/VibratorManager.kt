package com.example.cashluckpatrol

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService

class VibratorManager (private val context : Context) {




    private val vibratorManager : VibratorManager? =
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as Vibrator?


    private val vibrator : Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? Vibrator
    }
    else {
        context.getSystemService((Context.VIBRATOR_SERVICE) as? Vibrator
    }
}