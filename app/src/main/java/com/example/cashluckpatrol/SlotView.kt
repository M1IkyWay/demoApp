package com.example.cashluckpatrol

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class SlotView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {


    //draw an image and set it
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return true
    }
}
