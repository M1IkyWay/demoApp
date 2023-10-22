package com.example.cashluckpatrol

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.cashluckpatrol.R
import kotlinx.android.synthetic.main.slot_image_scroll.view.*

class SlotScroll: LinearLayout {

    internal lateinit var eventEnd: EventEnd
    internal var lastResult = 0
    internal var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(nextImage.tag.toString())

    fun setEventEnd(eventEnd: EventEnd){
        this.eventEnd = eventEnd
    }

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context)
    }

    private fun init(context: Context){
        LayoutInflater.from(context).inflate(R.layout.slot_image_scroll, this)
        nextImage.translationY = height.toFloat()
    }

    fun setRandomValue(image:Int, numRoll:Int){
        currentImage.animate()
            .translationY(-height.toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        nextImage.translationY = nextImage.height.toFloat()
        nextImage.animate()
            .translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    setImage(currentImage, oldValue%6)
                    currentImage.translationY = 0f
                    if(oldValue != numRoll) {
                        setRandomValue(image,numRoll)
                        oldValue++
                    }
                    else {
                        lastResult = 0
                        oldValue = 0
                        setImage(nextImage, image)
                        eventEnd.eventEnd(image%6, numRoll)

                    }

                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }

            }).start()

    }

    //!! symbol is for asserting non-null to variables
    private fun setImage(currentImage: ImageView?, value: Int){
        when (value) {
            Utils.image1HG -> currentImage!!.setImageResource(R.drawable.hot_im1)
            Utils.image2HG -> currentImage!!.setImageResource(R.drawable.hot_im2)
            Utils.image3HG -> currentImage!!.setImageResource(R.drawable.hot_im3)
            Utils.image4GH -> currentImage!!.setImageResource(R.drawable.hot_im4)
            Utils.image5HG -> currentImage!!.setImageResource(R.drawable.hot_im5)
        }

        currentImage!!.tag = value
        lastResult = value
    }



}