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
    lateinit var musicService : MusicService
    var theEnd = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val endOfLast = intent.getIntExtra("endOfSong", 1000)

        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()
        musicService = MusicService(soundVolume, R.raw.launcher, this)
        musicService.playMusic(endOfLast)


        scoreViewModel.score.observe( this, { newscore ->
            binding.currentBal.setText("$newscore")
        })

        binding.playHotButton.setOnClickListener {
            AnimationHelper.pressingAnimation(it, binding.hotPlayText)
            val intent = Intent(this, HotGameActivity ::class.java)
            startActivity(intent)
            musicService.stopMusic()
        }

        binding.playButtonSlot1.setOnClickListener {
            AnimationHelper.clickView(it, this)
            val intent = Intent(this, SlotGame1Activity::class.java)
            startActivity(intent)
            musicService.stopMusic()
        }

        binding.playButtonSlot2.setOnClickListener {
            AnimationHelper.clickView(it, this)
            val intent = Intent(this, SlotGame2Activity::class.java)
            startActivity(intent)
            musicService.stopMusic()
        }

        binding.playButtonFlash1.setOnClickListener {
            AnimationHelper.clickView(it, this)
            val intent = Intent(this, FlashGame1Activity::class.java)
            startActivity(intent)
            musicService.stopMusic()
        }

        binding.playButtonFlash2.setOnClickListener {
            AnimationHelper.clickView(it, this)
            val intent = Intent(this, FlashGame2Activity::class.java)
            startActivity(intent)
            musicService.stopMusic()
        }

        binding.slot3.setOnClickListener {
            AnimationHelper.clickView(it, this)
            val intent = Intent(this, ComingSoonActivity::class.java)
            startActivity(intent)

        }

        binding.flash3.setOnClickListener {
            AnimationHelper.clickView(it, this)
            val intent = Intent(this, ComingSoonActivity::class.java)
            val name = "name"
            intent.putExtra("name",name)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        theEnd = musicService.findTheEnd()
        musicService.stopMusic()

    }
    override fun onResume() {
        super.onResume()
        musicService.playMusic(theEnd)
    }
    //Are you sure you want to leave
}