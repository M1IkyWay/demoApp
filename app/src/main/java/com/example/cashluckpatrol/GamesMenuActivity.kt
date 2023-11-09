package com.example.cashluckpatrol

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityGamesMenuBinding

class GamesMenuActivity : AppCompatActivity() {

    lateinit var scoreViewModel : ScoreViewModel
    lateinit var binding : ActivityGamesMenuBinding
    lateinit var soundHelper: SoundHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        soundHelper = (application as MyApplication).soundHelper
        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()


        scoreViewModel.score.observe( this, { newscore ->
            binding.currentBal.setText("$newscore")
        })


        binding.settindsButton.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            val intent = Intent(this, SettingsActivity ::class.java)
            startActivity(intent)
        }


        binding.playHotButton.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            val intent = Intent(this, HotGameActivity ::class.java)
            startActivity(intent)
        }

        binding.playButtonSlot1.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            val intent = Intent(this, SlotGame1Activity::class.java)
            startActivity(intent)
        }

        binding.playButtonSlot2.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            val intent = Intent(this, SlotGame2Activity::class.java)
            startActivity(intent)
        }

        binding.playButtonFlash1.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            val intent = Intent(this, FlashGame1Activity::class.java)
            startActivity(intent)
        }

        binding.playButtonFlash2.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            val intent = Intent(this, FlashGame2Activity::class.java)
            startActivity(intent)

        }

        binding.slot3.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            startActivity(Intent(this, ComingSoonActivity::class.java))

        }

        binding.flash3.setOnClickListener {
            soundHelper.clickSound(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            startActivity(Intent(this, ComingSoonActivity::class.java))
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

    //Are you sure you want to leave
}