package com.example.cashluckpatrol

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.SecureRandom
import java.util.HashMap
import kotlin.properties.Delegates

class HotGameActivity : AppCompatActivity() {


    lateinit var scoreViewModel : ScoreViewModel
    lateinit var spinButton : ImageView
    lateinit var binding : ActivityHotGameBinding
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHotGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scoreViewModel = (application as MyApplication).scoreViewModel
        successGame = false
        currentBet = 200
        var lastPressedBet : View? = null
        var winsCount = 0
        val scope = CoroutineScope (Dispatchers.Main)

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

        val betList = listOf(binding.bet50, binding.bet100, binding.bet150, binding.bet200, binding.bet250,
            binding.bet300, binding.bet350, binding.bet500)
        betList.forEach {
            var isPressed = false
        }

        scoreViewModel.score.observe( this, { newscore ->
            binding.resultBalance.setText("$newscore")
        })

        betList.forEach {
            it.setOnClickListener {
                val bet = it.tag.toString()
                currentBet = bet.toInt()
                Log.d("$currentBet", "the currentBet is nowwwwwwwwwwwwwwwwwwwwwwwww")

            AnimationHelper.updateScoreOrBetTextViewAnimation(binding.choosenBet, bet)
            AnimationHelper.pressingAnimation(it, null)
            AnimationHelper.buttonIsPressed(it, lastPressedBet)
            lastPressedBet = it
            }
        }

        val slots = setupSlotsMachine()
        spinButton = binding.btnSpin
        spinButton.setOnClickListener {
            if (scoreViewModel.getScore()>=currentBet) {
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
        val builder = SlotsBuilder().Builder(this)
        val slots = builder
            .addSlots(R.id.slot_one, R.id.slot_two, R.id.slot_three)
            .addDrawables(
                R.drawable.hot_im1,
                R.drawable.hot_im2,
                R.drawable.hot_im3,
                R.drawable.hot_im4,
                R.drawable.hot_im5
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
                        Log.d("${successGame}", "тут зашли в элс часть проигрыша, $currentBet")
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




}



