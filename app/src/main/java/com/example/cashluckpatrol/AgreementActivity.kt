package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {


    lateinit var soundHelper: SoundHelper
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var binding : ActivityAgreementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()

        soundHelper = (application as MyApplication).soundHelper


        binding.privPolButton.setOnClickListener {
            AnimationHelper.clickView(it, this)
            soundHelper.clickSound(this, soundVolume)
            startActivity(Intent(this, PrivacyPolicy::class.java))


        }

        binding.yesButton.setOnClickListener {
            AnimationHelper.smallClickView(it, this)
            soundHelper.clickSound(this, soundVolume)
            startActivity(Intent(this, GamesMenuActivity::class.java))

        }

        binding.noButton.setOnClickListener {
            AnimationHelper.smallClickView(it, this)
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