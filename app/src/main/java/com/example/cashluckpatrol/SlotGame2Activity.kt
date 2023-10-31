package com.example.cashluckpatrol

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding
import com.example.cashluckpatrol.databinding.ActivitySlotGame2Binding
import kotlin.random.Random

class SlotGame2Activity : AppCompatActivity() {

    lateinit var betText : TextView
    lateinit var btnUp : ImageView
    lateinit var btnDown : ImageView
    lateinit var binding: ActivitySlotGame2Binding
    private var bet: Int = 0
    var totalWin: Int = 0
    lateinit var scoreViewModel: ScoreViewModel
    private var handler = Handler(Looper.getMainLooper())
    private var increment = 1

    private fun updateValue(view: View) {
        when (view) {
            binding.btnUp -> {
                bet += increment
                betText.setText(bet.toString())
            }

            binding.btnDown -> if (bet > 0) {
                bet -= increment
                betText.setText(bet.toString())
            } else {
                AnimationHelper.wrongInputAnimation(betText)
            }
        }
    }
    inner class RepeatAction(val view: View) : Runnable {
        override fun run() {
            updateValue(view)
            handler.postDelayed(this, 200)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlotGame2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        scoreViewModel = (application as MyApplication).scoreViewModel

        scoreViewModel.score.observe(this) { newScore ->
            val newTextScore = newScore.toString()
            AnimationHelper.updateScoreOrBetTextViewAnimation(binding.balance, newTextScore)
        }

        btnDown = binding.btnDown
        btnUp = binding.btnUp
        betText = binding.betNumber

//warning was supressed
        btnUp.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed(RepeatAction(view), 200)
            } else if (event.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
            }
            true
        }

        btnDown.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed(RepeatAction(view), 200)
            } else if (event.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
            }
            true
        }


        fun spinCircle(): Float {
            var multiplier: Float = 0.0f
            val circles = Random.nextInt(6, 11)
            val degrees = Random.nextInt(0, 360)
            val totalDegrees = circles + degrees

            when (degrees) {
                in (0 until 36) -> multiplier = 2.0f
                in (36 until 72) -> multiplier = 1.0f
                in (72 until 108) -> multiplier = 2.0f
                in (108 until 144) -> multiplier = 1.5f
                in (144 until 180) -> multiplier = 1.0f
                in (180 until 216) -> multiplier = 0.0f
                in (216 until 252) -> multiplier = 2.0f
                in (252 until 288) -> multiplier = 1.5f
                in (288 until 324) -> multiplier = 1.0f
                in (324 until 360) -> multiplier = 0.0f
            }
            return multiplier
        }


        binding.btnRotate.setOnClickListener {
            it.isEnabled = false

            if (bet == 0) {
                val toast = Toast.makeText(this, "Set the bet, please!", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val multiplier = spinCircle()
                val thisWin = (bet * multiplier).toInt()
                totalWin += thisWin
                AnimationHelper.updateScoreOrBetTextViewAnimation(
                    binding.scoreWin,
                    totalWin.toString()
                )
                scoreViewModel.countScoreSlot2(bet, multiplier)


            }
            it.isEnabled = true
        }

    }
}
