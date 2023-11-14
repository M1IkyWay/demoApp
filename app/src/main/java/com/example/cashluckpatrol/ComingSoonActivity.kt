package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.cashluckpatrol.databinding.ActivityComingSoonBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ComingSoonActivity : AppCompatActivity() {
    lateinit var binding : ActivityComingSoonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        super.onCreate(savedInstanceState)
        binding = ActivityComingSoonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val scope = CoroutineScope (Dispatchers.Main)


        scope.launch {

//            repeat(7) {
//                YoYo.with(Techniques.ZoomInLeft).duration(700).playOn(binding.coomingSoon)

            YoYo.with(Techniques.FadeInLeft).duration(700).playOn(binding.coomingSoon)
                delay(700)
//                YoYo.with(Techniques.BounceIn).duration(700).playOn(binding.coomingSoon)

//            }

        }



    }

}