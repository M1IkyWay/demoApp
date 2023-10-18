package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityGamesMenuAnonBinding
import com.example.cashluckpatrol.databinding.ActivityGamesMenuBinding

class GamesMenuAnonActivity : AppCompatActivity() {
    lateinit var binding : ActivityGamesMenuAnonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesMenuAnonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playHotButton.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.playButtonSlot1.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.slot2.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.slot3.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.playButtonFlash1.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.flash2.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.flash3.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity ::class.java)
            startActivity(intent)
        }

    }

}