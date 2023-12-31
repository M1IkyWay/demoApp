package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlayBinding
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var soundHelper: SoundHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val soundService = Intent (this, SoundService::class.java)
        startService(soundService)

        soundHelper = (application as MyApplication).soundHelper
        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()
        val intensity = scoreViewModel.getVibroIntensity()


        fun openNextActivity (intent: Intent) {
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

            finish()
        }

        AnimationHelper.appearingButton(binding.playButton, binding.text)




        binding.playButton.setOnClickListener{
            AnimationHelper.pressingAnimation(it, binding.text)
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound(this, soundVolume)
            YoYo.with(Techniques.SlideOutLeft).duration(1000).playOn(binding.playButton)
            YoYo.with(Techniques.SlideOutLeft).duration(1000).playOn(binding.text)

                openNextActivity(Intent(this, GamesMenuActivity::class.java))
        }

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


