package com.example.cashluckpatrol

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log

object SoundHelper {

    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_GAME)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()


    val soundPool = SoundPool.Builder()
        .setMaxStreams(3)
        .setAudioAttributes(audioAttributes)
        .build()



    fun slotMachineSound (context: Context, loud : Float) {
        val winSound = soundPool.load(context, R.raw.slot_machine, 1)
        soundPool.play(winSound, loud, loud, 1, 0, 1.0f)
        Log.d("The music started ", " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    }

    fun slotMachineSound (context: Context, loud : Float) {
        val winSound = soundPool.load(context, R.raw.slot_machine, 1)
        soundPool.play(winSound, loud, loud, 1, 0, 1.0f)
        Log.d("The music started ", " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
    }




    fun  release () {
        soundPool.release()
    }
}


