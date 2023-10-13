package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityEnteringPhoneBinding

class EnteringPhoneActivity : AppCompatActivity() {
    lateinit var binding : ActivityEnteringPhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEnteringPhoneBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}