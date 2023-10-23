package com.example.cashluckpatrol

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.cashluckpatrol.databinding.SlotImageScrollBinding

class SlotMachineView (context : Context) : LinearLayout (context) {

//    public constructor(
//        context: Context, attributeSet: AttributeSet, rawsNumb: Int, columnNumb: Int,
//        images: List<ImageView>
//    ) : this(context) {}
    val image1 = ContextCompat.getDrawable(context, R.drawable.hot_im1)
    val image2 = ContextCompat.getDrawable(context, R.drawable.hot_im2)
    val image3 = ContextCompat.getDrawable(context, R.drawable.hot_im3)
    val image4 = ContextCompat.getDrawable(context, R.drawable.hot_im4)
    val image5 = ContextCompat.getDrawable(context, R.drawable.hot_im5)


    var imagesList = mutableListOf (image1, image2, image3, image4, image5)

        init {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.slot_image_scroll, this, true)
            val binding = SlotImageScrollBinding.inflate(inflater)

            binding.column1.children.forEach {


            }

        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()



    }


    }



