package com.example.cashluckpatrol

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.core.graphics.luminance
import com.example.cashluckpatrol.databinding.ActivityEnteringEmailBinding

class EnteringEmailActivity : AppCompatActivity() {

    lateinit var binding : ActivityEnteringEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnteringEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    var email = ""
    val emailInput = binding.emailInput
        emailInput.baseline.luminance //what have i done?
    val playButton = binding.playButton
    val validity = binding.textValidity

        fun isEmailValid(email: String) : Boolean {
            val emailRegex = Regex("[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,}")
            return email.matches(emailRegex)
        }

        emailInput.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus) {
                (view as EditText).hint = ""
                (view as EditText).setSelection(0)}
            else if (!emailInput.text.toString().equals("")) {
                email = emailInput.text.toString()
                if (isEmailValid(email)) {
                    validity.setText(R.string.email_is_valid)
                    validity.getResources().getColor(R.color.validity_color)
                }
                else {
                    validity.setText(R.string.email_is_invalid)
                    validity.getResources().getColor(R.color.invalidity_color)
                }
            }
        }

        playButton.setOnClickListener {
            email = emailInput.text.toString()
            if (isEmailValid(email)) {
                val intent = Intent(this, GamesMenuActivity::class.java)
                startActivity(intent)
            }
            else {
                val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
                validity.startAnimation(shakeAnimation)
                //vibration
            }
        }
    }






}