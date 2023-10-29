package com.example.cashluckpatrol

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log

class SoundManager (context: Context)  {
    private val soundPool: SoundPool

    init {
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()
        Log.d("aaaaaaaaaaaaaaaaaaaaaaaa", "soundPoolObject was createeeeeeeeeeeeeeeeeeeeeeeeeg")
    }
        fun playBackSound(context : Context, soundId : Int, loop : Int = -1, volume : Float) {
            val _soundId = soundPool.load(context, soundId, 1)
            soundPool.play(_soundId, volume, volume, 1, loop, 1.0f )
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaa", "soundPoolObject was playyyyyyyyyyyyyyyyyyyyyed")
        }

        fun release() {
            soundPool.release()
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaa", "soundPoolObject was releaseeeeeeeeeeeeeeeeeeeeeeeeeeeeeed")
        }

    }