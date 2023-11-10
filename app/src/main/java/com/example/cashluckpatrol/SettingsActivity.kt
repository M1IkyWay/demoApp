package com.example.cashluckpatrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.widget.ImageView
import androidx.core.content.getSystemService
import com.example.cashluckpatrol.databinding.ActivitySettingsBinding
import kotlin.properties.Delegates

class SettingsActivity : AppCompatActivity() {


    lateinit var scoreViewModel : ScoreViewModel
    lateinit var binding : ActivitySettingsBinding
    lateinit var soundHelper: SoundHelper
    var currentVolume by Delegates.notNull<Int>()
    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        soundHelper = (application as MyApplication).soundHelper
        scoreViewModel = (application as MyApplication).scoreViewModel


        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)



          val sLevel1 = SoundVibroViewClass(binding.grade1, 1, false)
          val sLevel2 = SoundVibroViewClass(binding.grade2, 2, false)
          val sLevel3 = SoundVibroViewClass(binding.grade3, 3, false)
          val sLevel4 = SoundVibroViewClass(binding.grade4, 4, false)
          val sLevel5 = SoundVibroViewClass(binding.grade5, 5, false)
          val sLevel6 = SoundVibroViewClass(binding.grade6, 6, false)
          val sLevel7 = SoundVibroViewClass(binding.grade7, 7, false)
          val sLevel8 = SoundVibroViewClass(binding.grade8, 8, false)

            val listOfSoundLevels = listOf<SoundVibroViewClass>(sLevel1, sLevel2, sLevel3,
            sLevel4, sLevel5, sLevel6, sLevel7, sLevel8)



            fun soundSettings () {


                listOfSoundLevels.forEach {
                    it.square.setOnClickListener {
                        val itsLevel = it.tag.toString().toInt()
                        it as ImageView

                        if (itsLevel == 1 && listOfSoundLevels[0].wasClicked == true) {
                            listOfSoundLevels.forEach {
                                it.square.setImageResource(R.drawable.non_active_rect)
                            }
                            scoreViewModel.updateSoundVolume(0f)
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
                        } else {
                            it.setImageResource(R.drawable.active_rect)
                            val newSoundLevel = (maxVolume / 8 * itsLevel).toFloat()
                            scoreViewModel.updateSoundVolume(newSoundLevel)
                            audioManager.setStreamVolume(
                                AudioManager.STREAM_MUSIC,
                                newSoundLevel.toInt(),
                                0
                            )
                            for (i in 0 until itsLevel) {
                                listOfSoundLevels[i].square.setImageResource(R.drawable.active_rect)
                            }
                            if (itsLevel<8) {
                                for (i in itsLevel until 8) {
                                    listOfSoundLevels[i].square.setImageResource(R.drawable.non_active_rect)
                                }
                            }

                        }
                        if (listOfSoundLevels[itsLevel - 1].wasClicked == true) {
                            listOfSoundLevels[itsLevel - 1].wasClicked = false
                        } else {
                            listOfSoundLevels[itsLevel - 1].wasClicked = true
                        }
                    }
                }
            }


        soundSettings()

//        scoreViewModel.soundVolume.observe( this, { newLevel ->
//            currentVolume = newLevel
//        })
//
//
//
//        val soundVolume = scoreViewModel.getSoundVolume()
//        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//
//        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
//        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
//

//
//
//        volumeLevels.forEachIndexed { index, volumeLevel ->
//           volumeLevel.setOnClickListener {
//               val newVolume = (index + 1) * (maxVolume/volumeLevels.size)
//               volumeLevel.setImageResource(R.drawable.active_rect)
//               for (i in 0  until index ) {
//                   volumeLevels[i].setImageResource(R.drawable.active_rect)
//           }
//                for (i in index+1 until volumeLevels.size) {
//                    volumeLevels[i].setImageResource(R.drawable.non_active_rect)
//                }
//               audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0)
//           }
//        }
//
//
//





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


//        val maxAmplitude = 250
//
//        vibrationStrengthLevels.forEachIndexed {index, vibrationLevel ->
//            vibrationLevel.setOnClickListener {
//                val newVibrationLevel =  (index + 1) / 8 * maxAmplitude
//                vibrationLevel.setImageResource(R.drawable.active_rect)
//                for (i in 0  until index ) {
//                    vibrationStrengthLevels[i].setImageResource(R.drawable.active_rect)
//                }
//                for (i in index+1 until volumeLevels.size-1) {
//                    vibrationStrengthLevels[i].setImageResource(R.drawable.non_active_rect)
//                }
//                VibrationEffect.createOneShot(100L, newVibrationLevel)
//
//            }
//        }

//написати як повністю вимкнути вібро і звук при подвійному танисканні
    }


    override fun onPause() {
        super.onPause()
        val soundService = Intent (this, SoundService::class.java)
        soundService.action = "pauseMusic"
        startService(soundService)
    }

    override fun onResume() {
        super.onResume()
        val soundService = Intent (this, SoundService::class.java)
        soundService.action = "resumeMusic"
        startService(soundService)
    }
}