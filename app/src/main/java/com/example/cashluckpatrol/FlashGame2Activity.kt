package com.example.cashluckpatrol

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProviders
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
    lateinit var binding: ActivityFlashGame2Binding
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    private var handler = Handler(Looper.getMainLooper())
    var theEnd = 0
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val scope = CoroutineScope(Dispatchers.Main)
        spinButton = binding.btnSpin

        val listOfButtons = mutableListOf<ImageView>(
            binding.num1, binding.num2, binding.num3,
            binding.num4, binding.num5, binding.num6, binding.num7, binding.num8, binding.num9
        )

        listOfButtons.forEach {
            it.setOnClickListener {
                it.isEnabled = false

                val seed = System.currentTimeMillis()
                val random = Random(seed)
                val image = random.nextInt(0, 1)
                val drawable: Drawable

                if (image == 0) {
                    drawable = AppCompatResources.getDrawable(this, R.drawable.bomb)!!
                } else {
                    drawable = AppCompatResources.getDrawable(this, R.drawable.money)!!
                }
                val view = it as ImageView
                scope.launch {
                    AnimationHelper.rotateBackward(it)
                    delay(600)
                    view.setImageDrawable(drawable)
                }

                if (image == 1) {
                    count += 1
                }
                else {
                    //sad music
                //open all buttons


                   //take bet back
                    }

                if (count == 4) {
                    //bet*2



                }



            }

            fun spinViews() {
                listOfButtons.forEach {
                    scope.launch {
                        AnimationHelper.rotateForward(it)
                        delay(600)
                        it.setImageResource(R.drawable.quest)
                        it.isEnabled = true
                    }
                }
            }

            spinButton.setOnClickListener {
                spinViews()
            }

        }


    }

}