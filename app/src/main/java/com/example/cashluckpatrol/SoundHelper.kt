package com.example.cashluckpatrol

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
class SoundHelper (context: Context) {

    private var soundPool : SoundPool
    private var winSound : Int = 0
    private var loseSoundId : Int = 0
    private var clickSoundDefault : Int = 0
    private var clickSound2 : Int = 0
    private var slotMachineSound : Int = 0
    private var slot2win : Int = 0
    private var wheelSpin : Int = 0
    private var wrongInput : Int = 0
    private var explosion : Int = 0
    private var mistake : Int = 0
    private var rotate : Int = 0
    private var correctInput : Int = 0

   init {
       val audioAttributes = AudioAttributes.Builder()
           .setUsage(AudioAttributes.USAGE_GAME)
           .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
           .build()

       soundPool = SoundPool.Builder()
           .setMaxStreams(3)
           .setAudioAttributes(audioAttributes)
           .build()


//       winSound = soundPool.load(context, R.raw.)

        clickSoundDefault = soundPool.load(context, R.raw.click_default, 1)
        slotMachineSound = soundPool.load(context, R.raw.slot_machine, 1)
        clickSound2 = soundPool.load(context, R.raw.click2, 1)
        slot2win = soundPool.load(context, R.raw.slot2_win, 1)
        loseSoundId = soundPool.load(context, R.raw.defeat_1,1)
        winSound = soundPool.load(context, R.raw.win, 1)
        wheelSpin = soundPool.load(context, R.raw.wheel_spin, 1)
        wrongInput = soundPool.load(context, R.raw.wrong_input, 1)
        explosion = soundPool.load(context, R.raw.explosion, 1)
        mistake = soundPool.load(context, R.raw.mistake_choice, 2)
        rotate = soundPool.load(context, R.raw.rotate, 1)
        correctInput = soundPool.load(context, R.raw.correct_input, 1)
   }





    fun slotMachineSound (context: Context, loud : Float) {
        soundPool.play(slotMachineSound, loud, loud, 1, 0, 1.0f)
    }

    fun clickSound (context: Context, loud : Float) {
        soundPool.play(clickSoundDefault, loud, loud, 1, 0, 1.0f)
    }

    fun clickSound2 (context: Context, loud : Float) {
        soundPool.play(clickSound2, loud, loud, 1, 0, 1.0f)
    }

    fun slot2winSound (context: Context, loud: Float) {
        soundPool.play(slot2win, loud, loud, 1, 0, 1.0f)

    }

    fun winSound (context : Context, loud: Float) {
        soundPool.play(winSound, loud, loud, 1, 0, 1.0f)

    }

    fun wheelSpinSound (context : Context, loud: Float) {
        soundPool.play(wheelSpin, loud, loud, 1, 0, 1.0f)

    }

    fun loseSound (context : Context, loud: Float) {
        soundPool.play(loseSoundId, loud, loud, 1, 0, 1.0f)

    }

    fun wrongInputSound (context : Context, loud: Float) {
        soundPool.play(wrongInput, loud, loud, 1, 0, 1.0f)

    }

    fun explosionSound (context: Context, loud: Float) {
        soundPool.play(explosion, loud, loud, 1, 0, 1.0f)
        soundPool.play(mistake, loud, loud, 1, 0, 1.0f)

    }

    fun rotateSound (context: Context, loud: Float) {
        soundPool.play(rotate, loud, loud, 1, 0, 1.0f)
    }

    fun correctInputSound (context: Context, loud: Float) {
        soundPool.play(correctInput, loud, loud, 1, 0, 1.0f)
    }


    fun  release () {
        soundPool.release()
    }

    fun pause () {
        soundPool.autoPause()
    }

    fun resume () {
        soundPool.autoResume()
    }

}


