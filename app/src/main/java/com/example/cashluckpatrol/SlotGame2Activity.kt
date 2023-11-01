package com.example.cashluckpatrol

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivitySlotGame2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    private var increment = 5
    private var resultMultiplier = 0f

    private fun updateValue(view: View) {


        when (view) {
            binding.btnUp -> {
                betText.setTextColor(Color.WHITE)
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
            handler.postDelayed(this, 150)
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

        val scope = CoroutineScope (Dispatchers.Main)

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

       fun degreesMap (degrees : Int) : Float {
            var multiplier = 0f
            when (degrees) {
                in (0 until 36) -> multiplier = 0.0f //0   //old 2.0f
                in (36 until 72) -> multiplier = 1.0f //1
                in (72 until 108) -> multiplier = 1.5f //1.5   //old 2.0f
                in (108 until 144) -> multiplier = 2.0f //2    // old 1.5f
                in (144 until 180) -> multiplier = 0.0f //0    // pld 1.0f
                in (180 until 216) -> multiplier = 1.0f //1   //old 0.0f
                in (216 until 252) -> multiplier = 1.5f //1.5  //old is 2.0f
                in (252 until 288) -> multiplier = 2.0f //2    // old is 1.5f
                in (288 until 324) -> multiplier = 1.0f //1  //
                in (324 until 360) -> multiplier = 2.0f//2 old is 0.0f
            }
            return multiplier
        }

        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down)

        fun createPopup (multiplier: Float) {
            if (multiplier > 1.0f) {
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.popup_slot2, null)

                val popupWindow = PopupWindow (
                    view,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true)

                popupWindow.contentView.startAnimation(slideUpAnimation)
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                handler.postDelayed({
//                    popupWindow.dismiss()}, 1000)

                popupWindow.contentView.startAnimation(slideDownAnimation)
                    handler.postDelayed({
                        popupWindow.dismiss()},
                        slideDownAnimation.duration.toLong())}, 1000)
    }
            }

        fun spinCircle(): Float {
            val circles = Random.nextInt(6, 10)
            val degrees = Random.nextInt(0, 360)
            val totalDegrees = circles*360 + degrees
            Log.d("there is degreeeees", "degrees are $degrees aaaaaaaaaaaaaaaaaaaaaaaaaa")


                val rotateAnimation = RotateAnimation(0f,
                    totalDegrees.toFloat(), Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f)
                rotateAnimation.duration = 5000
                rotateAnimation.interpolator = DecelerateInterpolator()
                rotateAnimation.fillAfter = true
                binding.foregroundCircle.startAnimation(rotateAnimation)

                resultMultiplier = degreesMap(degrees)
            return resultMultiplier
        }



        binding.btnRotate.setOnClickListener {
            it.isEnabled = false
            val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate)

            binding.arrow.startAnimation(rotate)
            AnimationHelper.clickView(it, this)
            binding.roundArrow.startAnimation(rotate)


            if (bet == 0) {
                val toast = Toast.makeText(this, "Set the bet, please!", Toast.LENGTH_SHORT)
                toast.show()
                AnimationHelper.wrongInputAnimation(binding.betNumber)
                it.isEnabled = true

            } else {
                scope.launch {
                    val multiplier = spinCircle()
                    delay(5500)
                    val thisWin = (bet * multiplier).toInt()
                    Log.d("there is multiplier", "mupliplier is $multiplier aaaaaaaaaaaaaaaaaa")
                    totalWin += thisWin
                    AnimationHelper.updateScoreOrBetTextViewAnimation(
                        binding.scoreWin,
                        totalWin.toString()
                    )
                    scoreViewModel.countScoreSlot2(bet, multiplier)
                    createPopup(multiplier)
                }
                it.isEnabled = true


            }
        }
    }
}
