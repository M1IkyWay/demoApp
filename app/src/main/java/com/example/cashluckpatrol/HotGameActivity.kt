package com.example.cashluckpatrol

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding

class HotGameActivity : AppCompatActivity() {

    lateinit var binding : ActivityHotGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slotMachine = SlotMachineView(this)


        binding.btnSpin.setOnClickListener{


        }



    }





}