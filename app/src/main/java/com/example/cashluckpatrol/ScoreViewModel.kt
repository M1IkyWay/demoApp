package com.example.cashluckpatrol

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {

   var score : MutableLiveData<Int> = MutableLiveData()

    init {
        score = MutableLiveData(2000)

    }

    fun getScore () : Int {
        val value : Int? = score.value
        if (value!=null) {
            return value
        }
        else {
            return 0
        }
    }


    fun updateScore (newScore : Int) {
        score.value = newScore
    }

    fun countResult (bet : Int, multiplier : Int, win : Boolean) {

        if (win) {
            val result = bet * multiplier
           updateScore(getScore() + result)
            Log.d("the code works as it is true win", "wiiiiiiiiiiiin, ${getScore() + result}")
        }
        else {
            Log.d("the code works ", "код зашел в блок елс в каунт резалт, ${getScore()}")
            if (getScore() > bet) {
                updateScore(getScore() - bet)
                Log.d("the code works as it is not win", "wiiiiiiiiiiiiiiiiiiiin, ${getScore() - bet}")
            }
            else {
                updateScore(0)
            }
        }
    }


}