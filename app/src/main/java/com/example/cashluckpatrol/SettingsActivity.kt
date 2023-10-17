package com.example.cashluckpatrol

import android.content.Context
import android.media.AudioManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.example.cashluckpatrol.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var binding : ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

     val volumeLevels = listOf(
         binding.grade1,
         binding.grade2,
         binding.grade3,
         binding.grade4,
         binding.grade5,
         binding.grade6,
         binding.grade7,
         binding.grade8,
     )
        volumeLevels.forEachIndexed { index, volumeLevel ->
           volumeLevel.setOnClickListener {
               val newVolume = (index + 1) * (maxVolume/volumeLevels.size)
               volumeLevel.setImageResource(R.drawable.active_rect)
               for (i in 0  until index ) {
                   volumeLevels[i].setImageResource(R.drawable.active_rect)
           }
                for (i in index+1 until volumeLevels.size-1) {
                    volumeLevels[i].setImageResource(R.drawable.non_active_rect)
                }
               audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
           }
        }
//set default value

        val vibrationStrengthLevels = listOf(
            binding.vgrade1,
            binding.vgrade2,
            binding.vgrade3,
            binding.vgrade4,
            binding.vgrade5,
            binding.vgrade6,
            binding.vgrade7,
            binding.vgrade8,

        )


        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }


        val maxAmplitude = 250

        vibrationStrengthLevels.forEachIndexed {index, vibrationLevel ->
            vibrationLevel.setOnClickListener {
                val newVibrationLevel =  (index + 1) / 8 * maxAmplitude
                vibrationLevel.setImageResource(R.drawable.active_rect)
                for (i in 0  until index ) {
                    vibrationStrengthLevels[i].setImageResource(R.drawable.active_rect)
                }
                for (i in index+1 until volumeLevels.size-1) {
                    vibrationStrengthLevels[i].setImageResource(R.drawable.non_active_rect)
                }
                VibrationEffect.createOneShot(100L, newVibrationLevel)

            }
        }

//написати як повністю вимкнути вібро і звук при подвійному танисканні
    }
}