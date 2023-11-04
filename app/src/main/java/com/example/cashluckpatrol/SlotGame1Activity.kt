package com.example.cashluckpatrol

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlotGame1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        soundHelper = (application as MyApplication).soundHelper
        scoreViewModel = (application as MyApplication).scoreViewModel
        successGame = false
        currentBet = 200

        var winsCount = 0
        val scope = CoroutineScope (Dispatchers.Main)
        val soundVolume = scoreViewModel.getSoundVolume()*0.7f
        musicService = MusicService(soundVolume, R.raw.slot1, this)
        musicService.playMusic(0)

        spinButton = binding.btnSpin

        suspend fun updateWinsCount (isWin : Boolean) {
            if (isWin) {
                winsCount+=1
                binding.winsCount.setText("${winsCount}")
                val toast = Toast.makeText (this, "You win!", Toast.LENGTH_SHORT)
                toast.show()
                //add music and vibro
            }
            else {
                val toast = Toast.makeText (this, "You lose!", Toast.LENGTH_SHORT)
                toast.show()
//add music and vibro
            }

        }
        scoreViewModel.score.observe( this, { newscore ->
            binding.resultBalance.setText("$newscore")
        })

        binding.decremBet.setOnClickListener {
            AnimationHelper.clickView ( it, this)
            if (currentBet>50) {
                currentBet-=50
                AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
            }
            else {

                AnimationHelper.wrongInputAnimation(binding.choosenBet)
                val toast = Toast.makeText(this, "Minimal bet is 50", Toast.LENGTH_SHORT)
            }
        }

        binding.incremBet.setOnClickListener {
            AnimationHelper.clickView ( it, this)
            binding.choosenBet.setTextColor(Color.WHITE)
                currentBet += 50
            AnimationHelper.updateAnotherBetOrScore(currentBet, binding.choosenBet)
            }

        val slots = setupSlotsMachine()


        spinButton.setOnClickListener {
            Log.d("spin button was clicked", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb")
            AnimationHelper.clickSLot1(this, binding.btnSpin)
            AnimationHelper.clickView ( it, this)
            it.isEnabled = false
            AnimationHelper.clickSLot1(this, it)
            if (scoreViewModel.getScore()>=currentBet) {
                soundHelper.slotMachineSound(this, soundVolume)
                scope.launch {
                    slots.start()
                    delay(5500)
                    updateWinsCount(successGame)
                }
            }
            else {
                val toast = Toast.makeText(this, "You don`t have enough money!", Toast.LENGTH_SHORT)
                toast.show()
                //add some vibro here
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


    override fun onPause() {
        super.onPause()
        theEnd = musicService.findTheEnd()
        musicService.stopMusic()

    }
    override fun onResume() {
        super.onResume()
        musicService.playMusic(theEnd)
    }


}
