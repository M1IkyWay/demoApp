package com.example.cashluckpatrol

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivitySlotGame1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.HashMap
import kotlin.properties.Delegates

class SlotGame1Activity : AppCompatActivity() {

    lateinit var musicService : MusicService
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var binding : ActivitySlotGame1Binding
    lateinit var spinButton : ImageView
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    var theEnd = 0
    var winsCount by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivitySlotGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        scoreViewModel = (application as MyApplication).scoreViewModel
        winsCount = 0

        if (savedInstanceState!=null) {
            currentBet = savedInstanceState.getInt("currentBet")
            winsCount = savedInstanceState.getInt("count")
            theEnd = savedInstanceState.getInt("theEndOfMusic")


            // и остальные
        }


        fun getStartBet (score: Int) : Int {
            val currentBet = ((score/100)*5)
            val roundedBet = kotlin.math.round(currentBet/10.0) * 10
            return roundedBet.toInt()
        }
        currentBet = getStartBet(scoreViewModel.getScore())
        binding.choosenBet.setText(currentBet.toString())
        spinButton = binding.btnSpin
        soundHelper = (application as MyApplication).soundHelper
        successGame = false

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }


        val scope = CoroutineScope (Dispatchers.Main)
        val intensity = scoreViewModel.getVibroIntensity()
        val soundVolume = scoreViewModel.getSoundVolume()*0.7f
        musicService = MusicService(soundVolume*0.7f, R.raw.slot1, this)
        musicService.playMusic(theEnd)



        suspend fun updateWinsCount (isWin : Boolean) {
            if (isWin) {
                winsCount+=1

                binding.winsCount.setText("${winsCount}")
                val toast = Toast.makeText (this, "You win!", Toast.LENGTH_SHORT)
                soundHelper.winShot(intensity, vibrator)

                toast.show()
                //add music and vibro

            }
            else {
                soundHelper.loseSound(this, soundVolume)
                soundHelper.defeatShot(intensity, vibrator)
                val toast = Toast.makeText (this, "You lose!", Toast.LENGTH_SHORT)
                toast.show()
//add music and vibro
            }

        }
        scoreViewModel.score.observe( this, { newscore ->
            binding.resultBalance.setText("$newscore")
        })

        binding.decremBet.setOnClickListener {
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
                val toast = Toast.makeText(this, "Minimal bet is 10", Toast.LENGTH_SHORT)
            }
        }

        binding.incremBet.setOnClickListener {
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.clickView ( it, this)
            binding.choosenBet.setTextColor(Color.WHITE)
                currentBet += 10
            AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
            }

        val slots = setupSlotsMachine()


        spinButton.setOnClickListener {
            binding.choosenBet.setTextColor(Color.WHITE)
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            it.isEnabled = false

            if (scoreViewModel.getScore()>=currentBet) {
                binding.resultBalance.setTextColor(resources.getColor(R.color.input_color))
                AnimationHelper.smallClickView(it, this)
                soundHelper.slotMachineSound(this, soundVolume)
                soundHelper.spinShot(intensity, vibrator)
                scope.launch {
                    slots.start()
                    delay(5500)
                    updateWinsCount(successGame)
                    it.isEnabled = true
                }
            }
            else {
                AnimationHelper.wrongInputAnimation(binding.resultBalance)
                soundHelper.vibroWarning(intensity, vibrator)
                soundHelper.wrongInputSound(this, soundVolume)
                val toast = Toast.makeText(this, "You don`t have enough money!", Toast.LENGTH_SHORT)
                toast.show()
                //add some vibro here
                it.isEnabled = true
            }
        }

        }

    private fun setupSlotsMachine() : SlotsBuilder {
        Log.d("slot machine was set up", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        val builder = SlotsBuilder().Builder(this)
        val slots = builder
            .addSlots(R.id.slot_one, R.id.slot_two, R.id.slot_three)
            .addDrawables(
                R.drawable.q_lit,
                R.drawable.num_lit,
                R.drawable.k_lit,
                R.drawable.j_lit,
                R.drawable.man_lit
            )
            .setScrollTimePerInch(0.5f)
            .setDockingTimePerInch(0f)
            .setScrollTime(500 + SecureRandom().nextInt(1500))
            .setChildIncTime(1000)
            .setOnFinishListener(object : Callback() {
                override fun onFinishListener() {
                    val layoutManagers = getLayoutManagers()
                    val match = HashMap<Int, Int>()
                    for (i in 0 until 3) {
                        val imageView = layoutManagers.get(i)
                            .findViewByPosition(
                                (layoutManagers.get(i)
                                    .findFirstVisibleItemPosition() + 1) //+3
                            ) as ImageView
                        val drawableId = imageView.tag as Int

                        match[drawableId] = match.getOrDefault(drawableId, 0) + 1 //is it needed?
                    }

                    var resultMatch = 0
                    match.values.forEach { value ->
                        if (resultMatch < value) {
                            resultMatch = value
                        }
                    }

                    if (resultMatch == 3 && !binding.btnSpin.isEnabled) {
                        successGame = true
                        scoreViewModel.countResult(currentBet, 2, successGame)
//                        Log.d("${scoreViewModel.countResult(currentBet, 2, successGame)}", "здесь добавлен результат")
                        //add visual changes
                        binding.btnSpin.isEnabled = true
                        AnimationHelper.updateScoreOrBetTextViewAnimation(binding.resultBalance, scoreViewModel.getScore().toString())
                        scoreViewModel.updateLevel(scoreViewModel.getLevel() + 1)
                    }
                    else {

                        successGame = false
                        scoreViewModel.countResult(currentBet, 2, successGame)
//                        Log.d("${scoreViewModel.countResult(currentBet, 2, successGame)}", "здесь посчитан в минус результат $successGame")
                        binding.btnSpin.isEnabled = true
                    }
                    AnimationHelper.updateScoreOrBetTextViewAnimation(binding.resultBalance, scoreViewModel.getScore().toString())
                }
            })
            .build()
        return slots
    }



    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentBet", currentBet)
        outState.putInt("winsCount", winsCount)
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
