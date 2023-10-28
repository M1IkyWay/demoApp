package com.example.cashluckpatrol

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
        val result = bet * multiplier
        if (win) {
           updateScore(getScore() + result)
        }
        else {
            if (getScore() > result) {
                updateScore(getScore() - result)
            }
            else {
                updateScore(0)
            }
        }
    }


}