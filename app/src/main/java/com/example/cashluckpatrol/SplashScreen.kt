package com.example.cashluckpatrol

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.cashluckpatrol.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SplashScreen : AppCompatActivity() {
    lateinit var binding : ActivitySplashScreenBinding

    fun startDownloading (intent : Intent) {
        val parentLayout = binding.downloadSet
        val activeRect : Drawable = ContextCompat.getDrawable(this, R.drawable.active_rect)!!
        val scope = CoroutineScope (Dispatchers.Main)

        scope.launch {
            parentLayout.children.forEach {
                it as ImageView
                it.setImageDrawable(activeRect)
                val delayLong = Random.nextInt(400) + 50
                delay(delayLong.toLong())
            }
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
}
    override fun onStart() {
        super.onStart()
        val intent = Intent( this, PlayActivity ::class.java)
        startDownloading(intent)
    }
    }
