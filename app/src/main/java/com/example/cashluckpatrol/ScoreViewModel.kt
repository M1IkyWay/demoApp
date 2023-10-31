package com.example.cashluckpatrol

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

   var score : MutableLiveData<Int> = MutableLiveData()

   var vibroIntensity : MutableLiveData<Float> = MutableLiveData()

   var soundVolume : MutableLiveData<Float> = MutableLiveData()



    init {
        score = MutableLiveData(2000)
        vibroIntensity = MutableLiveData(1.0f)
        soundVolume = MutableLiveData(1.0f)
    }


    fun getScore () : Int {
        val value : Int? = score.value
        if (value!=null) return value
        else return 0
        }

    fun updateScore (newScore : Int) {
        score.value = newScore
    }

    fun countResult (bet : Int, multiplier : Int, win : Boolean) {
       if (win) {
                val result = bet * multiplier
                updateScore(getScore() + result)
            }
        else {
           updateScore(getScore() - bet)
        }
        }


    fun getVibroIntensity () : Float {
    val value : Float? = vibroIntensity.value
    if (value!=null) return value
    else return 0f
    }

    fun updateVibroIntensity (newIntensity : Float) {
        vibroIntensity.value = newIntensity
    }

    fun getSoundVolume () : Float {
        val value : Float? = soundVolume.value
        if (value!=null) return value
        else return 0f
    }

    fun updateSoundVolume (newVolume : Float) {
        soundVolume.value = newVolume
    }

    fun countScoreSlot2 (bet : Int, multiplier : Float) {
        when {
            multiplier == 0.0f -> updateScore(getScore()-bet)
            multiplier > 1.0f -> updateScore((getScore()+(multiplier*bet).toInt()))
            else -> updateScore(getScore())
        }


    }

}