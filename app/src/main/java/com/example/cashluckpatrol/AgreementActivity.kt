package com.example.cashluckpatrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityAgreementBinding

class AgreementActivity : AppCompatActivity() {

    lateinit var binding : ActivityAgreementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgreementBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.privPolButton.setOnClickListener {
            //animation
            val intent = Intent(this, PrivacyPolicy::class.java)
            startActivity(intent)
        }

        binding.yesButton.setOnClickListener {
            //animation
            val intent = Intent(this, Se::class.java)
            startActivity(intent)
        }
    }


}