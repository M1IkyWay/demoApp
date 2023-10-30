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

//        fun isServiceRunning(serviceClass: Class<MusicService>) : Boolean {
//            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//            var isPlayed = false
//            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
//                if (serviceClass.name == service.service.className) {
//                    isPlayed =  true
//                }
//                else isPlayed = false
//            }
//            return isPlayed
//        }
//
//        if (!isServiceRunning(MusicService::class.java)) {
//                val soundVolume = scoreViewModel.getSoundVolume()
//                val musicService = MusicService()
//            val intent = Intent (this, MusicService::class.java)
//            intent.putExtra("soundVolume", soundVolume)
//            intent.putExtra("musicResourse", R.raw.launcher)
//            startService(intent)
//
//        }
//        fun closeMusicService () {
//            val serviceIntent = Intent (this, MusicService::class.java)
//            stopService(serviceIntent)
//        }


        binding.playHotButton.setOnClickListener {
            AnimationHelper.pressingAnimation(it, binding.hotPlayText)
            val intent = Intent(this, HotGameActivity ::class.java)
            startActivity(intent)
            musicService.stopMusic()


        }

        binding.playButtonSlot1.setOnClickListener {
            val intent = Intent()
            startActivity(intent)

        }

        binding.playButtonSlot2.setOnClickListener {

            val intent = Intent()
            startActivity(intent)

        }

        binding.playButtonFlash1.setOnClickListener {
            val intent = Intent()
            startActivity(intent)

        }

        binding.playButtonFlash2.setOnClickListener {
            val intent = Intent()
            startActivity(intent)

        }

        binding.slot3.setOnClickListener {
            val intent = Intent()
            startActivity(intent)

        }

        binding.flash3.setOnClickListener {
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