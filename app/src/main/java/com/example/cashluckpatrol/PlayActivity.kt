package com.example.cashluckpatrol

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlayBinding
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var musicService : MusicService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()

//        musicService = MusicService(soundVolume, R.raw.launcher)
        val intent = Intent (this, MusicService::class.java)
        intent.putExtra("soundVolume", soundVolume)
        intent.putExtra("musicResourse", R.raw.launcher)
        startService(intent)
        AnimationHelper.appearingButton(binding.playButton, binding.text)

//        val soundManager = SoundManager(this)
//        soundManager.playBackSound(this, R.raw.launcher, -1, soundVolume)
//        soundManager.release()


        binding.playButton.setOnClickListener{
            AnimationHelper.pressingAnimation(it, binding.text)


            YoYo.with(Techniques.SlideOutLeft).duration(1000).playOn(binding.playButton)
            YoYo.with(Techniques.SlideOutLeft).duration(1000).playOn(binding.text)
            val intent = Intent(this, GamesMenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

            finish()
        }
    }
}


