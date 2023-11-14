package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityComingSoonBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ComingSoonActivity : AppCompatActivity() {

    lateinit var soundHelper: SoundHelper
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var binding : ActivityComingSoonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivityComingSoonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scoreViewModel = (application as MyApplication).scoreViewModel
        soundHelper = (application as MyApplication).soundHelper
        val scope = CoroutineScope (Dispatchers.Main)
        val soundVolume = scoreViewModel.getSoundVolume()
        val intensity = scoreViewModel.getVibroIntensity()




        binding.buttBack.setOnClickListener {
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            onBackPressed()
        }


        scope.launch {

//            repeat(7) {
//                YoYo.with(Techniques.ZoomInLeft).duration(700).playOn(binding.coomingSoon)

            YoYo.with(Techniques.FadeInLeft).duration(700).playOn(binding.coomingSoon)
                delay(700)
//                YoYo.with(Techniques.BounceIn).duration(700).playOn(binding.coomingSoon)

//            }

        }

        binding.buttBack.setOnClickListener {
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            onBackPressed()
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