package com.example.cashluckpatrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.cashluckpatrol.databinding.ActivityEnteringPhoneBinding
import com.google.android.material.textfield.TextInputLayout
import me.ibrahimsn.lib.PhoneNumberKit

class EnteringPhoneActivity : AppCompatActivity() {
    lateinit var binding : ActivityEnteringPhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEnteringPhoneBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val inputPhone = findViewById<EditText>(R.id.numberInput)

        val phoneNumberKit = PhoneNumberKit.Builder(this)
            .setIconEnabled(true)
            .admitCountries(listOf("ua", "ca", "de")) // List only those county formats
            .build()


        phoneNumberKit.attachToInput(binding.textField, "tr")

        phoneNumberKit.attachToInput(binding.textField, 1)

        phoneNumberKit.setupCountryPicker(this) // Requires activity context
        val exampleNumber = phoneNumberKit.getExampleNumber("ua")

        val parsedNumber = phoneNumberKit.parsePhoneNumber(
            number = "1266120000",
            defaultRegion = "ua"
        )

        parsedNumber?.nationalNumber
        parsedNumber?.countryCode
        parsedNumber?.numberOfLeadingZeros

        val formattedNumber = phoneNumberKit.formatPhoneNumber(
            number = "1266120000",
            defaultRegion = "us"
        )

        val flag = phoneNumberKit.getFlagIcon("ua")

        phoneNumberKit.setupCountryPicker(this, R.layout.edittext_layout, searchEnabled = true)

    }
}