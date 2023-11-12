package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        super.onCreate(savedInstanceState)
        binding = ActivityComingSoonBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val scope = CoroutineScope (Dispatchers.Main)


        scope.launch {
            YoYo.with(Techniques.BounceInUp).duration(300).playOn(binding.coomingSoon)
            YoYo.with(Techniques.BounceInDown).duration(300).playOn(binding.coomingSoon)
            delay(1000)
        }



    }

}