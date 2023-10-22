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

        val image1 = ContextCompat.getDrawable(this, R.drawable.hot_im1)
        val image2 = ContextCompat.getDrawable(this, R.drawable.hot_im2)
        val image3 = ContextCompat.getDrawable(this, R.drawable.hot_im3)
        val image4 = ContextCompat.getDrawable(this, R.drawable.hot_im4)
        val image5 = ContextCompat.getDrawable(this, R.drawable.hot_im5)

        val imageList = mutableListOf (image1, image2, image3, image4, image5)



        val ColumnScroll1 = binding.column1
        val scrollContent1 = binding.scrollContentColumn1
        val ColumnScroll2 = binding.column2
        val scrollContent2 = binding.scrollContentColumn2
        val ColumnScroll3 = binding.column3
        val scrollContent3 = binding.scrollContentColumn3
    }

}