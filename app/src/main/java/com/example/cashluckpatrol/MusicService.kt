package com.example.cashluckpatrol

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicService () : Service() {

//    class MusicService (volume : Float, soundId : Int) : Service() {

    private lateinit var mediaplayer : MediaPlayer
    private var currentResourseId = 0
    private  var _volume = 1.0f

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return super.onStartCommand(intent, flags, startId)
        val volume = intent?.getFloatExtra("soundVolume", 1.0f)
        val musicResourse = intent?.getIntExtra("musicResourse", R.raw.launcher)
        _volume = volume ?: 1.0f
        currentResourseId = musicResourse ?: R.raw.launcher

        return START_STICKY

    }



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        mediaplayer = MediaPlayer.create(this, currentResourseId)
        mediaplayer.isLooping = true
        mediaplayer.setVolume(_volume, _volume)
        mediaplayer.start()
        Log.d("fffffffffffffffffff", "the music was started")
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaplayer.stop()
        mediaplayer.release()
    }
}