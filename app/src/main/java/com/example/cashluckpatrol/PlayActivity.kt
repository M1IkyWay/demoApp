package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityPlayBinding

class PlayActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlayBinding
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var musicService : MusicService
    var theEnd = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()
        val sound = R.raw.launcher



        AnimationHelper.appearingButton(binding.playButton, binding.text)
        musicService = MusicService(soundVolume, sound, this)
        musicService.playMusic(0)


        binding.playButton.setOnClickListener{
            AnimationHelper.pressingAnimation(it, binding.text)

            YoYo.with(Techniques.SlideOutLeft).duration(1000).playOn(binding.playButton)
            YoYo.with(Techniques.SlideOutLeft).duration(1000).playOn(binding.text)

            val endOfSong = musicService.findTheEnd()
            musicService.stopMusic()
            val intent = Intent(this, GamesMenuActivity::class.java)
            intent.putExtra("endOfSong", endOfSong)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

            musicService.onDestroy()
            finish()
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
}


