package com.example.cashluckpatrol

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService

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
    private var defeatFlash1 : Int = 0


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
        defeatFlash1 = soundPool.load(context, R.raw.defeat_flash1, 1)


//       vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//           val vibratorManager =
//               getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
//           vibratorManager.defaultVibrator
//       } else {
//           @Suppress("DEPRECATION")
//           getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
//       }





   }

   val vibrator : Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator




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

    fun defeatFlash1 (context: Context, loud: Float) {
        soundPool.play(defeatFlash1, loud, loud, 1, 0, 1.0f)
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



    fun defeatShot (intensity : Int, vibratorr : Vibrator) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity,
                intensity, intensity)
            val array : LongArray = longArrayOf(0, 500, 100, 300, 100, 100)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            vibratorr.vibrate(vibrationEffect)
            vibratorr.cancel()
        }
        else {
        vibratorr.vibrate(200)
        }
    }

    fun vibroClick (intensity : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibrationEffect = VibrationEffect.createOneShot(80, intensity)
            vibrator.vibrate(vibrationEffect)
            vibrator.cancel()
        }
        else {
            vibrator.vibrate(100)

        }
    }

    fun winShot (intensity : Int, vibratorr: Vibrator) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array1 : LongArray = longArrayOf(0, 100, 100, 100)
            val array2 : LongArray = longArrayOf(100, 300, 70, 500)
            val vibrationEffect1 = VibrationEffect.createWaveform(array1, intensityArr, 1)
            val vibrationEffect2 = VibrationEffect.createWaveform(array2, intensityArr, 1)
            vibratorr.vibrate(vibrationEffect1)
            vibratorr.vibrate(vibrationEffect2)
            vibratorr.cancel()
        }
        else {
            vibratorr.vibrate(100)
            vibratorr.vibrate(300)
            vibratorr.vibrate(500)
        }
    }

    fun spinShot (intensity : Int, vibratorr: Vibrator) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val array1 : LongArray = longArrayOf(50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50)
            val intensityArr1 = intArrayOf(intensity, intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity)

            val intensityArr2 = intArrayOf(intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity,
                intensity)
            val array2 = longArrayOf(0, 50, 50, 100, 50, 300, 50, 500, 50, 1000)

            val vibrationEffect1 = VibrationEffect.createWaveform(array1, intensityArr1, 5)
            val vibrationEffect2 = VibrationEffect.createWaveform(array2, intensityArr2, 1)

//            Log.d("fffffffffffffffff", "first vibration starts")
//            vibrator.vibrate(vibrationEffect1)
//            Log.d("fffffffffffffffff", "second vibration starts")
//            vibrator.vibrate(vibrationEffect1)
//            Log.d("fffffffffffffffff", "third vibration starts")
            vibratorr.vibrate(vibrationEffect1)
//            vibrator.vibrate(vibrationEffect1)
            vibratorr.cancel()
            vibratorr.vibrate(vibrationEffect2)
            vibratorr.cancel()
        }
        else {
            vibratorr.vibrate(50)
            vibratorr.vibrate(100)
            vibratorr.vibrate(200)
        }
    }

    fun vibroWarning (intensity : Int, vibratorr: Vibrator) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity,
                intensity, intensity)
            val array : LongArray = longArrayOf(0, 100, 50, 100, 50, 100)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            vibratorr.vibrate(vibrationEffect)
            vibratorr.cancel()
        }
        else {
            vibratorr.vibrate(100)
            vibratorr.vibrate(100)
            vibratorr.vibrate(100)
        }
    }

    fun vibroExplosion (intensity : Int, vibratorr: Vibrator) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 100, 50, 200, 50, 300)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            vibratorr.vibrate(vibrationEffect)
            vibratorr.cancel()
        }
        else {
            vibratorr.vibrate(200)
            vibratorr.vibrate(400)
        }
    }

    fun vibroPopup (intensity : Int, vibratorr: Vibrator, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 300, 50, 100)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            vibratorr.vibrate(vibrationEffect)
            vibratorr.cancel()
        }
        else {
            vibratorr.vibrate(200)
            vibratorr.vibrate(400)
        }
    }

}


