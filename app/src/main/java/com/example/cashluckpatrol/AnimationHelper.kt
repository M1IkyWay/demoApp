package com.example.cashluckpatrol

import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

object AnimationHelper {


    fun pressingAnimation(viewBase: View, viewText : View?) {
        YoYo.with(Techniques.Pulse).duration(300).playOn(viewBase)
        viewText?.let{
//            YoYo.with(Techniques.FadeOut).duration(300).playOn(viewText)
            YoYo.with(Techniques.FadeIn).duration(150).playOn(viewText)
        }
        }

    fun changingButtonUI (view: View) {
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

    fun buttonIsPressed(view: View, prevPressedView : View?) {
        if (prevPressedView!=null) {
            prevPressedView.isPressed = false
            changingButtonUI(prevPressedView)
            Log.d(prevPressedView?.tag.toString(), "ui of prevbutton was changed button was changed")

        }
        changingButtonUI(view)
        Log.d(view.tag.toString(), "ui of pressed button was changed")

    }

    ////разобраться с анимацией,которая не работает нормально

    fun updateScoreOrBetTextViewAnimation (textView: TextView, number : String) {
        YoYo.with(Techniques.BounceInUp).duration(300).playOn(textView)
        textView.setText(number)
        YoYo.with(Techniques.BounceInDown).duration(300).playOn(textView)
    }

    fun appearingButton (button: ImageView, text : TextView) {
        YoYo.with(Techniques.BounceInDown).duration(1200).playOn(button)
        YoYo.with(Techniques.BounceInDown).duration(1200).playOn(text)

    }

}



