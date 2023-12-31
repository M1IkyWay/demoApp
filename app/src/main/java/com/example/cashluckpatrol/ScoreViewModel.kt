package com.example.cashluckpatrol

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

   var score : MutableLiveData<Int> = MutableLiveData()

   var level : MutableLiveData<Int> = MutableLiveData()

   var vibroIntensity : MutableLiveData<Int> = MutableLiveData()

   var soundVolume : MutableLiveData<Float> = MutableLiveData()

   private var isPrivacyPolicyAccepted : MutableLiveData<Boolean> = MutableLiveData()

    init {
        score = MutableLiveData(2000)
        level = MutableLiveData(0)
        vibroIntensity = MutableLiveData(255)
        soundVolume = MutableLiveData(1.0f)
        isPrivacyPolicyAccepted = MutableLiveData(false)
    }

    fun getLevel() : Int {
        val value : Int? = level.value
        if (value!=null) return value
        else return 0
    }

    fun updateLevel (newScore : Int) {
        level.value = newScore
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


    fun getVibroIntensity () : Int {
    val value : Int? = vibroIntensity.value
    if (value!=null) return value
    else return 0
    }

    fun updateVibroIntensity (newIntensity : Int) {
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

    fun getPrivacyPolicyAccepted() : Boolean {
        val value : Boolean? = isPrivacyPolicyAccepted.value
        if (value!==null) return value
        else return false
    }

    fun setIsPrivacyPolicyAccepted(accepted : Boolean) {
        isPrivacyPolicyAccepted.value = accepted
    }




}