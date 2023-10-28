package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityGamesMenuBinding

class GamesMenuActivity : AppCompatActivity() {

    lateinit var binding : ActivityGamesMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playHotButton.setOnClickListener {
            AnimationHelper.pressingAnimation(it, binding.hotPlayText)
            val intent = Intent(this, HotGameActivity ::class.java)
            startActivity(intent)
        }

        binding.playButtonSlot1.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.playButtonSlot2.setOnClickListener {

            val intent = Intent()
            startActivity(intent)
        }

        binding.playButtonFlash1.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.playButtonFlash2.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.slot3.setOnClickListener {
            val intent = Intent()
            startActivity(intent)
        }

        binding.flash3.setOnClickListener {
            val intent = Intent(this, ComingSoonActivity::class.java)
            val name = "name"
            intent.putExtra("name",name)
            startActivity(intent)
        }

    }


}