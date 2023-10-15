package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashluckpatrol.databinding.ActivityEnteringPhoneBinding
import me.ibrahimsn.lib.PhoneNumberKit

class EnteringPhoneActivity : AppCompatActivity() {
    lateinit var binding : ActivityEnteringPhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEnteringPhoneBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val phoneNumberKit = PhoneNumberKit.Builder(this)
            .setIconEnabled(true)
            .admitCountries(listOf("tr", "ca", "de")) // List only those county formats
            .excludeCountries(listOf("tr", "ca")) // Exclude those county formats
            .build()

        phoneNumberKit.attachToInput(textField, "tr")
// OR
        phoneNumberKit.attachToInput(textField, 1)

    }
}