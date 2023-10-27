package com.example.cashluckpatrol

import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

object AnimationHelper {
    fun pressingAnimation(view: View) {
        YoYo.with(Techniques.Pulse).duration(200).playOn(view)
        }


    fun buttonIsPressed(view: View) {
        if (view.isPressed) {
            view.setBackgroundResource(R.drawable.transparent_background)
            view.requestLayout()
            view.isPressed = false
        }
        else {
            view.setBackgroundResource(R.drawable.button_pressed_background)
            view.layoutParams.width = (view.width * 0.9).toInt()
            view.layoutParams.height = (view.height * 0.9).toInt()
            view.requestLayout()
            view.isPressed = true
        }
    }

}



