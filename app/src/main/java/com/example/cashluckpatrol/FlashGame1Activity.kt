package com.example.cashluckpatrol

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.cashluckpatrol.databinding.ActivityFlashGame1Binding
import com.example.cashluckpatrol.databinding.ActivitySlotGame1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.properties.Delegates

class FlashGame1Activity : AppCompatActivity() {

    lateinit var musicService : MusicService
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var spinButton : ImageView
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    var theEnd = 0
    private val userViewModel by lazy { ViewModelProviders.of(this).get(ScoreViewModel::class.java)}
    lateinit var binding : ActivityFlashGame1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        fun getStartBet (score: Int) : Int {
            val currentBet = ((score/100)*5)
            val roundedBet = kotlin.math.round(currentBet/10.0) * 10
            return roundedBet.toInt()
        }
        currentBet = getStartBet(scoreViewModel.getScore())
        binding.choosenBet.setText(currentBet.toString())

        spinButton = binding.btnSpin
        soundHelper = (application as MyApplication).soundHelper
        scoreViewModel = (application as MyApplication).scoreViewModel

        var winsCount = 0
        val scope = CoroutineScope (Dispatchers.Main)
        val soundVolume = scoreViewModel.getSoundVolume()*0.7f
        musicService = MusicService(soundVolume*0.7f, R.raw.flash_1, this)
        musicService.playMusic(0)


        scoreViewModel.score.observe( this, { newscore ->
            binding.resultBalance.setText("$newscore")
        })



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