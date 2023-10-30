package com.example.cashluckpatrol

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

//class MusicService (volume : Float, soundId : Int) : Service() {

    class MusicService (volume : Float, soundId : Int, context : Context) {

        private var currentResourceId = soundId
        private  var _volume = volume
        private var _context = context
        var mediaPlayer: MediaPlayer = MediaPlayer.create(context, soundId)


   fun playMusic(endOfLast : Int) {
       if(!mediaPlayer.isPlaying) {
           mediaPlayer.isLooping = true
           mediaPlayer.setVolume(_volume, _volume)
           mediaPlayer.seekTo(endOfLast)
           mediaPlayer.start()
       }
   }

   fun pauseMusic() {
       if(mediaPlayer.isPlaying == true) {
           mediaPlayer.pause()
       }
   }

//   fun finsTheEnd() : Int {

//   }
    fun findTheEnd() : Int {
    var millisecondsOfSong = 0
    millisecondsOfSong = mediaPlayer.currentPosition
       return millisecondsOfSong
       }



    fun stopMusic() {

       if (mediaPlayer.isPlaying == true) {
           mediaPlayer.stop()

           mediaPlayer.prepare()
       }
   }
   fun onDestroy() {
          mediaPlayer.stop()
          mediaPlayer.release()
        }
    }
