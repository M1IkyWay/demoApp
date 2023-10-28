package com.example.cashluckpatrol

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.cashluckpatrol.databinding.ActivityFlashGame1Binding

class FlashGame1Activity : AppCompatActivity() {

    private val userViewModel by lazy { ViewModelProviders.of(this).get(ScoreViewModel::class.java)}
    lateinit var binding : ActivityFlashGame1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var currentBet = 200
        binding.choosenBet.setText(currentBet.toString())
        //реализовать с помощью лайвдата

        fun scroll (currentBet : Int) {
            var win = false

            if (win) {
                val currentWin = currentBet * 2 //is it right?
                val currentBalance = currentWin + 200
                binding.resultBalance.setText(currentBalance.toString())
            }
            else {
                val currentBalance = 200 - currentBet
                binding.resultBalance.setText(currentBalance.toString())
            }


        }

        binding.incremBet.setOnClickListener {
            //animation on touch
            if (currentBet<=4950) {
                currentBet+=50
                binding.choosenBet.setText(currentBet.toString())
            }
            else {
                //animation shake
            }
        }

        binding.decremBet.setOnClickListener {
            //animation on touch
            if (currentBet <= 100) {
                currentBet -= 50
                binding.choosenBet.setText(currentBet.toString())
            } else {
                //animation shake
            }
        }

        binding.btnSpin.setOnClickListener{
            //animation on touch + inactive during some time
            //и какой множитель должен быть в случае победы
            scroll(currentBet)
        }

    }

}