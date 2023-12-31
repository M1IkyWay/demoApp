package com.example.cashluckpatrol
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class SpeedManager : LinearLayoutManager {

    companion object {
        private var scrollTime = 2f

        fun setScrollTime(time: Float) {
            scrollTime = time
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView?.context) {

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@SpeedManager.computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return scrollTime
            }
        }

        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}
