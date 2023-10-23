package com.example.cashluckpatrol

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.indices
import com.example.cashluckpatrol.databinding.SlotImageScrollBinding

class SlotMachineView (context : Context) : LinearLayout (context) {

//    public constructor(
//        context: Context, attributeSet: AttributeSet, rawsNumb: Int, columnNumb: Int,
//        images: List<ImageView>
//    ) : this(context) {}
    val image0 = ContextCompat.getDrawable(context, R.drawable.hot_im1)?.let { ImageWithID(0, it) }
    val image1 = ContextCompat.getDrawable(context, R.drawable.hot_im2)?.let { ImageWithID(1, it) }
    val image2 = ContextCompat.getDrawable(context, R.drawable.hot_im3)?.let { ImageWithID(2, it) }
    val image3 = ContextCompat.getDrawable(context, R.drawable.hot_im4)?.let { ImageWithID(3, it) }
    val image4 = ContextCompat.getDrawable(context, R.drawable.hot_im5)?.let { ImageWithID(4, it) }


    var imagesList = mutableListOf (image0, image1, image2, image3, image4)
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val binding = SlotImageScrollBinding.inflate(inflater) //если что, это все было в блоке инит


        init {
            inflater.inflate(R.layout.slot_image_scroll, this, true)


            binding.column1.children.forEach { view ->
                imagesList.shuffle()
                if (view is ImageView) {
                    val i = indexOfChild(view)
                    view.setImageDrawable(imagesList[i]?.image)
                }
            }


            binding.column2.children.forEach { view ->
                imagesList.shuffle()
                if (view is ImageView) {
                    val i = indexOfChild(view)
                    view.setImageDrawable(imagesList[i]?.image)
                }
            }

            binding.column3.children.forEach { view ->
                imagesList.shuffle()
                if (view is ImageView) {
                    val i = indexOfChild(view)
                    view.setImageDrawable(imagesList[i]?.image)
                }
            }


        }


    fun startScrollAnimation () {

        val animator = ValueAnimator.ofInt(0, height)
        animator.duration = 4000
        animator.interpolator = AccelerateDecelerateInterpolator()

        animator.addUpdateListener {
            animation ->
            val value = animation.animatedValue as Int
            scrollTo(0, value)
        }

        animator.start()
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()



    }



}



