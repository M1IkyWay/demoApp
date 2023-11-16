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
import androidx.recyclerview.widget.LinearLayoutManager
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

        val contextA : Context = this


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

        fun createVibrator () : Vibrator {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
            return vibrator
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
                scope.launch{ soundHelper.winSound(contextA, soundVolume)
                    binding.winImage.isVisible = true
                    YoYo.with(Techniques.BounceInDown).duration(1200).playOn(binding.winImage)
                    binding.animationView.isVisible = true
                    soundHelper.winShot(intensity, createVibrator(), scope)
                    binding.animationView.playAnimation()
                    delay(800)
                    YoYo.with(Techniques.FadeInUp).duration(300).playOn(binding.winImage)
                    binding.animationView.isVisible = false
                    binding.winImage.isVisible = false
                    scoreViewModel.updateScore(scoreViewModel.getScore() + 1)
                }
                scoreViewModel.updateLevel(scoreViewModel.getLevel()+1)

            }
            else {
                scope.launch {
                    val toast = Toast.makeText (contextA, "You lose!", Toast.LENGTH_SHORT)
                    soundHelper.loseSound(contextA, soundVolume)
                    soundHelper.defeatShot(intensity, createVibrator(), scope)
                    toast.show()

                }
//add music and vibro
            }

        }

        scoreViewModel.score.observe( this, { newscore ->
            binding.resultBalance.setText("$newscore")
            AnimationHelper.updateScoreOrBetTextViewAnimation(binding.resultBalance, scoreViewModel.getScore().toString())
        })

        binding.buttBack.setOnClickListener {
            soundHelper.vibroClick(intensity)
            soundHelper.clickSound2(this, soundVolume)
            AnimationHelper.smallClickView(it, this)
            onBackPressed()
        }





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
                toast.show()
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
            it.isEnabled = false
            binding.incremBet.isEnabled = false
            binding.decremBet.isEnabled = false
            soundHelper.clickSound2(this, soundVolume)
            soundHelper.spinShot(intensity, createVibrator(), scope)
            AnimationHelper.clickView ( it, this)
            if (scoreViewModel.getScore()>=currentBet) {
                soundHelper.slotMachineSound(this, soundVolume)
                scope.launch {
                    slots.start()
                    delay(6000)
                    updateWinsCount(successGame)
                    it.isEnabled = true
                    binding.incremBet.isEnabled = true
                    binding.decremBet.isEnabled = true
                }
            }
            else {
                val toast = Toast.makeText(this, "You don`t have enough money!", Toast.LENGTH_SHORT)
                toast.show()
                soundHelper.vibroWarning(intensity, createVibrator(), scope)
                //add some sound
                it.isEnabled = true
            }
        }

        }

    private fun setupSlotsMachine() : SlotsBuilder {
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
                    fun checkForMatches() {
                        val layoutManagers = getLayoutManagers()


                        fun checkRow(layoutManager: LinearLayoutManager): Boolean {
                            val elements = mutableListOf<Int>()

                            for (position in 0 until layoutManager.childCount) {
                                val imageView = layoutManager.getChildAt(position) as ImageView
                                val drawableId = imageView.tag as Int
                                Log.d("еееееееееууууу", "tag isssssssssssssssssss $drawableId")
                                elements.add(drawableId)
                            }

                            for (element in elements) {
                                val count = elements.count { it == element }
                                if (count >= 3) {
                                    Log.d("aaaaaaaaaaaaaaaaaaaaa", "resultMatch iiiiiiiiiiiiiiis 3")
                                    return true
                                }
                            }
                            return false
                        }


                        fun handleMatch() {
                            successGame = true
                            scoreViewModel.countResult(currentBet, 2, successGame)
                            binding.btnSpin.isEnabled = true
                            Log.d(
                                "youuuuuuuuuuuuuuuuuuuuuuuuuuu",
                                "WWWWwwwwiiiiiiiiiiiiiiiiiiiiiiiiinnnnnnnnnnn"
                            )

                        }

                        fun handleNoMatch() {
                            successGame = false
                            scoreViewModel.countResult(currentBet, 2, successGame)
                            binding.btnSpin.isEnabled = true
                            AnimationHelper.updateScoreOrBetTextViewAnimation(
                                binding.resultBalance,
                                scoreViewModel.getScore().toString()
                            )
                            Log.d(
                                "youuuuuuuuuuuuuuuuuuuuuuuuuuu",
                                "Looooooooooooooooooooooooooooooooooooooooose"
                            )
                        }

//////////////////////////////////vertical

                        fun checkVertical(): Boolean {

                            for (i in 0 until 3) {
                                Log.d("ddddddddddd", "iiiiiiiiiiin verticals $i")
                                val match = checkRow(layoutManagers[i])
                                Log.d("ииииииииииииииии", "match WAS IN вертикальное is $match")
                                if (match == true) {

                                    return true
                                }
                            }
                            return false
                        }

                        //////////////////////////////////horizontal
                        fun checkHorizontal(): Boolean {

                            Log.d("ddddddddddd", "iiiiiiiiiiin horisontals")

                            fun check1h(): Boolean {
                                var elements1 = mutableListOf<Int>()

                                for (i in 0 until 3) {
                                    Log.d("ddddddddddd", "iiiiiiiiiiin horisontals $i")
                                    val imageView = layoutManagers[i].getChildAt(0) as ImageView
                                    val item = imageView.tag as Int
                                    Log.d("еееееееееууууу", "tag isssssssssssssssssss $item")
                                    elements1.add(item)
                                }

                                for (element in elements1) {
                                    val count = elements1.count { it == element }
                                    Log.d(
                                        "aaaaaaaaaaaaaaaaaaaaa",
                                        "count is iiiiiiiiiiiiiiis $count"
                                    )
                                    if (count == 3) {

                                        return true
                                    }
                                }
                                return false
                            }

                            fun check2h(): Boolean {
                                var elements2 = mutableListOf<Int>()
                                for (i in 0 until 3) {
                                    val imageView = layoutManagers[i].getChildAt(1) as ImageView
                                    val item = imageView.tag as Int
                                    Log.d("еееееееееууууу", "tag isssssssssssssssssss $item")
                                    elements2.add(item)
                                }
                                for (element in elements2) {
                                    val count = elements2.count { it == element }
                                    Log.d(
                                        "aaaaaaaaaaaaaaaaaaaaa",
                                        "count is iiiiiiiiiiiiiiis $count"
                                    )
                                    if (count == 3) {
                                        return true
                                    }
                                }

                                return false
                            }

                            fun check3h(): Boolean {
                                var elements3 = mutableListOf<Int>()
                                for (i in 0 until 3) {
                                    val imageView = layoutManagers[i].getChildAt(2) as ImageView
                                    val item = imageView.tag as Int
                                    Log.d("еееееееееууууу", "tag isssssssssssssssssss $item")
                                    elements3.add(item)
                                }
                                for (element in elements3) {
                                    val count = elements3.count { it == element }
                                    Log.d(
                                        "aaaaaaaaaaaaaaaaaaaaa",
                                        "count is iiiiiiiiiiiiiiis $count"
                                    )
                                    if (count == 3) {
                                        return true
                                    }
                                }
                                return false
                            }

                            if (check1h() || check2h() || check3h() == true) {
                                return true

                            } else return false
                        }

                        fun checkDiagonals(): Boolean {

                            Log.d("ddddddddddd", "iiiiiiiiiiin diagonals")

                            fun check1 () : Boolean {
                                var elements1 = mutableListOf<Int>()
                                Log.d("ddddddddddd", "fiiiiiiiiiirst diagonal")
                                for (i in 0 until 3) {
                                    val imageView = layoutManagers[i].getChildAt(i) as ImageView
                                    val item = imageView.tag as Int
                                    Log.d("еееееееееууууу", "tag isssssssssssssssssss $item")
                                    elements1.add(item)
                                }
                                for (element in elements1) {
                                    val count = elements1.count { it == element }
                                    Log.d(
                                        "aaaaaaaaaaaaaaaaaaaaa",
                                        "count is iiiiiiiiiiiiiiis $count"
                                    )
                                    if (count == 3) {
                                        return true
                                    }
                                }

                                return false
                            }


                            fun check2 () : Boolean {
                                var counter = 2
                                var elements2 = mutableListOf<Int>()
                                for (i in 0 until 3) {
                                    Log.d("ddddddddddd", "second diagonal")
                                    val imageView =
                                        layoutManagers[i].getChildAt(counter - i) as ImageView
                                    val item = imageView.tag as Int
                                    Log.d("еееееееееууууу", "tag isssssssssssssssssss $item")
                                    elements2.add(item)
                                }
                                for (element in elements2) {
                                    val count = elements2.count { it == element }
                                    Log.d(
                                        "aaaaaaaaaaaaaaaaaaaaa",
                                        "count is iiiiiiiiiiiiiiis $count"
                                    )
                                    if (count == 3) {
                                        return true
                                    }
                                }

                                return false
                            }
                            if (check1() || check2() == true) {
                                return true
                            }
                            else return false
                        }


                        if (checkVertical() || checkHorizontal() || checkDiagonals() == true) {
                            handleMatch()
                        }
                        else {
                            handleNoMatch()
                        }
                    }
                    checkForMatches()
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
