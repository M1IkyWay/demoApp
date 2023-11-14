package com.example.cashluckpatrol

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProviders
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityFlashGame2Binding
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import kotlin.random.Random

class FlashGame2Activity : AppCompatActivity() {

    lateinit var musicService: MusicService
    lateinit var scoreViewModel: ScoreViewModel
    lateinit var spinButton: ImageView
    lateinit var incremBut : ImageView
    lateinit var decremBut : ImageView
    lateinit var binding: ActivityFlashGame2Binding
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    var theEnd = 0
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        spinButton = binding.btnSpin
        scoreViewModel = (application as MyApplication).scoreViewModel

        fun getStartBet(score: Int): Int {
            val currentBet = ((score / 100) * 5)
            val roundedBet = kotlin.math.round(currentBet / 10.0) * 10
            return roundedBet.toInt()
        }

        currentBet = getStartBet(scoreViewModel.getScore())
        binding.choosenBet.setText(currentBet.toString())
        val soundVolume = scoreViewModel.getSoundVolume()
        musicService = MusicService(soundVolume*0.7f, R.raw.flash_2, this)
        musicService.playMusic(0)
        spinButton.isEnabled = true
        val scope = CoroutineScope(Dispatchers.Main)
        soundHelper = (application as MyApplication).soundHelper
        val intensity = scoreViewModel.getVibroIntensity()

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        val context : Context = this
        incremBut = binding.incremBet
        decremBut = binding.decremBet


        scoreViewModel.score.observe( this) { newScore ->
            binding.resultBalance.setText("$newScore")
            AnimationHelper.updateScoreOrBetTextViewAnimation(binding.resultBalance, newScore.toString())
        }

       decremBut.setOnClickListener {
           soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.clickView ( it, this)
            if (currentBet>19) {
                currentBet-=10
                AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
            }
            else {
                AnimationHelper.wrongInputAnimation(binding.choosenBet)
                soundHelper.wrongInputSound(this, soundVolume)
                val toast = Toast.makeText(this, "Minimal bet is 50", Toast.LENGTH_SHORT)
            }
        }

       incremBut.setOnClickListener {
            binding.choosenBet.setTextColor(resources.getColor(R.color.input_color))
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.clickView ( it, this)
            currentBet += 10
            AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)


        }

        val listOfButtons = mutableListOf(
            binding.num1, binding.num2, binding.num3,
            binding.num4, binding.num5, binding.num6, binding.num7, binding.num8, binding.num9
        )
        val listOfImages = mutableListOf(1, 1, 0, 1, 0, 0, 1, 1, 0)
        listOfImages.shuffle()

        fun getImage (tag : String) : Int {
            return listOfImages[tag.toInt()]
        }

        fun openRestImages () {
            listOfButtons.forEach {
                if (it.isEnabled) {
                    it.isEnabled = false
                }
                    val drawable: Drawable
                    val image = getImage(it.tag.toString())

                    if (image == 0) {
                        drawable = AppCompatResources.getDrawable(this, R.drawable.bomb)!!
                        scope.launch {
                            AnimationHelper.rotateBackward(it)
                            delay(400)
                            it.setImageDrawable(drawable)
                        }

                    } else {
                        drawable = AppCompatResources.getDrawable(this, R.drawable.money)!!
                        scope.launch {
                            AnimationHelper.rotateBackward(it)
                            delay(400)
                            it.setImageDrawable(drawable)
                        }
                }
            }
        }

        fun setImageForView (it : View) {
            val drawable: Drawable
            val image = getImage(it.tag.toString())
            val view = it as ImageView

            if (image == 0) {
                drawable = AppCompatResources.getDrawable(this, R.drawable.bomb)!!
                scope.launch {
                    AnimationHelper.rotateBackward(it)
                    delay(400)
                    view.setImageDrawable(drawable)
                    soundHelper.vibroExplosion(intensity, vibrator)
                    soundHelper.explosionSound(context, soundVolume)
                    YoYo.with(Techniques.Shake).duration(500).playOn(view)

                    val defeat = scoreViewModel.getScore() - currentBet
                    scoreViewModel.updateScore(defeat)
                    delay(400)
                    count = 0
                    AnimationHelper.updateScoreOrBetTextViewAnimation(binding.winsCount, count.toString())
                    openRestImages()
                    incremBut.isEnabled = true
                    decremBut.isEnabled = true
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }

            } else {
                drawable = AppCompatResources.getDrawable(this, R.drawable.money)!!
                scope.launch {
                    AnimationHelper.rotateBackward(it)
                    delay(400)
                    soundHelper.correctInputSound(context, soundVolume)
                    view.setImageDrawable(drawable)
                    count += 1
                    AnimationHelper.updateScoreOrBetTextViewAnimation(binding.winsCount, count.toString())
                }
            }
            if (count == 4) {
                val win = currentBet*2 + scoreViewModel.getScore()
                scoreViewModel.updateScore(win)
                count = 0
                incremBut.isEnabled = true
                decremBut.isEnabled = true
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                scoreViewModel.updateLevel(scoreViewModel.getLevel() + 1)

            }
        }

        listOfButtons.forEach {
            it.setOnClickListener {
                soundHelper.vibroClick(intensity)
                it.isEnabled = false
                setImageForView(it)
            }
            }


            fun spinViews() {
                listOfImages.shuffle()
                soundHelper.rotateSound(context, soundVolume)
                listOfButtons.forEach {
                    scope.launch {
                        AnimationHelper.rotateForward(it)
                        delay(400)
                        it.setImageResource(R.drawable.quest_reversed)
                        it.isEnabled = true
                    }
                }
            }

            spinButton.setOnClickListener {
                count = 0
                AnimationHelper.updateScoreOrBetTextViewAnimation(binding.winsCount, count.toString())
                soundHelper.vibroClick(intensity)
                binding.incremBet.isEnabled = false
                binding.decremBet.isEnabled = false
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
                it.isEnabled = false
                binding.choosenBet.setTextColor(resources.getColor(R.color.input_color))
                AnimationHelper.clickView ( it, this)
                soundHelper.clickSound2(this, soundVolume)

                spinViews()
                it.isEnabled = true

            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentBet", currentBet)
        outState.putInt("count", count)
        outState.putInt("theEndOfMusic", theEnd)

        super.onSaveInstanceState(outState)
    }


    override fun onPause() {
        super.onPause()
        theEnd = musicService.findTheEnd()
        musicService.stopMusic()
        soundHelper.pause()

    }
    override fun onResume() {
        super.onResume()
        musicService.playMusic(theEnd)
        soundHelper.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundHelper.pause()
    }
}

