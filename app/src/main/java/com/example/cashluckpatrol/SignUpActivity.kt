package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityEnteringPhoneBinding
import com.example.cashluckpatrol.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_sign_up)

        binding.signPhone.setOnClickListener {
            val intent = Intent(this, EnteringPhoneActivity::class.java)
            startActivity(intent)
        }

        binding.signEmail.setOnClickListener {
            val intent = Intent(this, EnteringEmailActivity::class.java)
            startActivity(intent)
        }
    }
}