package com.example.cashluckpatrol

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class SoundService : Service() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer =  MediaPlayer.create(this, R.raw.launcher)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent!=null) {
            when(intent.action) {
               "pauseMusic" -> pauseMusic()
                "resumeMusic" -> resumeMusic()
                "stopMusic" -> stopMusic()
            }
//            mediaPlayer.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun pauseMusic() {
        mediaPlayer.pause()
    }

    private fun resumeMusic() {
        mediaPlayer.start()
    }

    private fun stopMusic () {
        mediaPlayer.stop()
    }
}