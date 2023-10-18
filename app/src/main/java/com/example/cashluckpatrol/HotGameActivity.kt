package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding

class HotGameActivity : AppCompatActivity() {

    lateinit var binding : ActivityHotGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}