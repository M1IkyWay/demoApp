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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private var winFlash1 : Int = 0
    private var coin : Int = 0


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
        winFlash1 = soundPool.load(context, R.raw.win1flash, 1)
        coin = soundPool.load(context, R.raw.coin, 1)


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



    fun win1Flash (context: Context, loud : Float) {
        soundPool.play(winFlash1, loud, loud, 1, 0, 1.0f)
    }

    fun coin (context: Context, loud : Float) {
        soundPool.play(coin, loud, loud, 1, 0, 1.0f)
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



    fun defeatShot (intensity : Int, vibratorr : Vibrator, scope: CoroutineScope) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 120, 100, 80)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            scope.launch {
                vibratorr.vibrate(vibrationEffect)
                delay(300)
                vibratorr.cancel()
            }
        }
        else {
            scope.launch {
                vibratorr.vibrate(100)
                delay(100)
                vibratorr.cancel()
            }

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

    fun winShot (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array1 : LongArray = longArrayOf(0, 100, 100, 100)
            val array2 : LongArray = longArrayOf(100, 200, 70, 300)
            val vibrationEffect1 = VibrationEffect.createWaveform(array1, intensityArr, 1)
            val vibrationEffect2 = VibrationEffect.createWaveform(array2, intensityArr, 1)
            scope.launch {
                vibratorr.vibrate(vibrationEffect1)
                delay(300)
                vibratorr.vibrate(vibrationEffect2)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(100)
                delay(200)
                vibratorr.vibrate(300)
                delay(400)
                vibratorr.vibrate(500)
                cancel()
            }

        }
    }

    fun spinShotCircle (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val array1 : LongArray = longArrayOf(50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50)
            val intensityArr1 = intArrayOf(intensity, intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity)

            val intensityArr2 = intArrayOf(intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity,
                intensity)
            val array2 = longArrayOf(0, 50, 100, 50, 200, 50, 300, 50, 500, 50)

            val vibrationEffect1 = VibrationEffect.createWaveform(array1, intensityArr1, 3)
            val vibrationEffect2 = VibrationEffect.createWaveform(array2, intensityArr2, 1)

            scope.launch {
                vibratorr.vibrate(vibrationEffect1)
                delay(3000)
                vibratorr.vibrate(vibrationEffect2)
                delay(1350)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(50)
                delay(100)
                vibratorr.vibrate(100)
                delay(150)
                vibratorr.vibrate(200)
                vibratorr.cancel()
            }

        }
    }


    fun spinShot (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val array1 : LongArray = longArrayOf(50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50)
            val intensityArr1 = intArrayOf(intensity, intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity)

            val intensityArr2 = intArrayOf(intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity, intensity,
                intensity)
            val array2 = longArrayOf(0, 50, 100, 50, 200, 50, 300, 50, 500, 50)

            val vibrationEffect1 = VibrationEffect.createWaveform(array1, intensityArr1, 4)
            val vibrationEffect2 = VibrationEffect.createWaveform(array2, intensityArr2, 1)

            scope.launch {
                vibratorr.vibrate(vibrationEffect1)
                delay(4000)
                vibratorr.vibrate(vibrationEffect2)
                delay(1350)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(50)
                delay(100)
                vibratorr.vibrate(100)
                delay(150)
                vibratorr.vibrate(200)
                vibratorr.cancel()
            }

        }
    }

    fun vibroWarning (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 100, 100, 50)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)

            scope.launch {
                vibratorr.vibrate(vibrationEffect)
                delay(250)
                vibratorr.cancel()
            }


        }
        else {
            scope.launch {
                vibratorr.vibrate(100)
                delay(200)
                vibratorr.vibrate(100)
                delay(200)
                vibratorr.vibrate(100)
                vibratorr.cancel()

            }

        }
    }

    fun vibroExplosion (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 50, 100, 100, 100, 200)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            scope.launch{
                vibratorr.vibrate(vibrationEffect)
                delay(550)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(200)
                delay(250)
                vibratorr.vibrate(400)
                vibratorr.cancel()
            }

        }
    }

    fun vibroPopup (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 300, 50, 100)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            scope.launch {
                vibratorr.vibrate(vibrationEffect)
                delay(450)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(200)
                delay(250)
                vibratorr.vibrate(100)
                vibratorr.cancel()
            }

        }
    }


    fun winVibroFlash2 (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 100, 50, 300)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 1)
            scope.launch {
                vibratorr.vibrate(vibrationEffect)
                delay(450)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(100)
                delay(150)
                vibratorr.vibrate(300)
                vibratorr.cancel()
            }

        }
    }

    fun winVibroFlash1 (intensity : Int, vibratorr: Vibrator, scope: CoroutineScope) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intensityArr = intArrayOf(intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity,
                intensity, intensity, intensity, intensity,
                intensity, intensity, intensity)
            val array : LongArray = longArrayOf(0, 50, 100, 50, 100, 50, 100, 50, 100, 50, 50, 50, 50, 50, 50)
            val vibrationEffect = VibrationEffect.createWaveform(array, intensityArr, 2)
            scope.launch {
                vibratorr.vibrate(vibrationEffect)
                delay(2700)
                vibratorr.cancel()
            }

        }
        else {
            scope.launch {
                vibratorr.vibrate(50)
                delay(200)
                vibratorr.vibrate(50)
                delay(20)
                vibratorr.vibrate(200)
                delay(100)
                vibratorr.vibrate(100)
                vibratorr.cancel()
            }

        }
    }
}


