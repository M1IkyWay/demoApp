package com.example.cashluckpatrol

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.cashluckpatrol.databinding.ActivityHotGameBinding

class HotGameActivity : AppCompatActivity() {

    lateinit var binding : ActivityHotGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotGameBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val selectedBet : ImageButton? = null

        val slotMachine = SlotMachineView(this, null)

        fun betClicked (imageButton : View) {
            val bet = imageButton.tag.toString()
            binding.choosenBet.setText(bet)

        }

        val betList = listOf(binding.bet50, binding.bet100, binding.bet150, binding.bet200, binding.bet250,
            binding.bet300, binding.bet350, binding.bet500)

        betList.forEach {
            it.setOnClickListener {
                betClicked(it)

            }

        }




        }

    }




