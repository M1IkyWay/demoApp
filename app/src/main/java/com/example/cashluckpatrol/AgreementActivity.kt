package com.example.cashluckpatrol

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.cashluckpatrol.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {


    lateinit var soundHelper: SoundHelper
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var binding : ActivityAgreementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)


        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()
        val intensity = scoreViewModel.getVibroIntensity()


        soundHelper = (application as MyApplication).soundHelper


        binding.privPolButton.setOnClickListener {
            AnimationHelper.clickView(it, this)
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound(this, soundVolume)
            startActivity(Intent(this, WebViewActivity::class.java))


        }

        binding.yesButton.setOnClickListener {
            AnimationHelper.smallClickView(it, this)
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound(this, soundVolume)
            scoreViewModel.setIsPrivacyPolicyAccepted(true)
            startActivity(Intent(this, GamesMenuActivity::class.java))

        }

        binding.noButton.setOnClickListener {
            AnimationHelper.smallClickView(it, this)
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound(this, soundVolume)
            startActivity(Intent(this, PlayActivity::class.java))

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