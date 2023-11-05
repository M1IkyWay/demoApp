package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityFlashGame2Binding
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding
import kotlin.properties.Delegates

class FlashGame2Activity : AppCompatActivity() {

    lateinit var musicService : MusicService
    lateinit var scoreViewModel : ScoreViewModel
    lateinit var spinButton : ImageView
    lateinit var binding : ActivityFlashGame2Binding
    var successGame by Delegates.notNull<Boolean>()
    var currentBet by Delegates.notNull<Int>()
    lateinit var soundHelper: SoundHelper
    var theEnd = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashGame2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        val listOfButtons = mutableListOf<ImageView>(binding.num1, binding.num2, binding.num3,
            binding.num4, binding.num5, binding.num6, binding.num7, binding.num8, binding.num9)

        listOfButtons.forEach {
            it.setOnClickListener {
            AnimationHelper.rotateForward(this, it)

            }

        }

        fun spinViews () {
            listOfButtons.forEach {




            }






        }






    }
}