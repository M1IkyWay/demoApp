package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {


    lateinit var soundHelper: SoundHelper
    lateinit var musicService: MusicService
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var binding : ActivityAgreementBinding
    var theEnd = 0
    var endOfSong = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)


        scoreViewModel = (application as MyApplication).scoreViewModel
        val soundVolume = scoreViewModel.getSoundVolume()

        soundHelper = (application as MyApplication).soundHelper
        musicService = MusicService(soundVolume, R.raw.launcher, this)
        val endOfLast = intent.getIntExtra("endOfSong", 1000)
        musicService.playMusic(endOfLast)



        binding.privPolButton.setOnClickListener {
            AnimationHelper.clickView(it, this)
            soundHelper.clickSound(this, soundVolume)
            endOfSong = musicService.findTheEnd()
            musicService.stopMusic()
            val intent = Intent(this, PrivacyPolicy::class.java)
            musicService.onDestroy()
            startActivity(intent)


        }

        binding.yesButton.setOnClickListener {
            AnimationHelper.clickView(it, this)
            soundHelper.clickSound(this, soundVolume)
            endOfSong = musicService.findTheEnd()
            musicService.stopMusic()
            intent.putExtra("endOfSong", endOfSong)
            val intent = Intent(this, GamesMenuActivity::class.java)
            musicService.onDestroy()
            startActivity(intent)

        }

        binding.noButton.setOnClickListener {
            AnimationHelper.clickView(it, this)
            soundHelper.clickSound(this, soundVolume)
            endOfSong = musicService.findTheEnd()
            musicService.stopMusic()
            intent.putExtra("endOfSong", endOfSong)
            val intent = Intent(this, PlayActivity::class.java)
            musicService.onDestroy()
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

}