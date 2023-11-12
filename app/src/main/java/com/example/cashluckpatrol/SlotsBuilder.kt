package com.example.cashluckpatrol

import android.app.Activity
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager

class SlotsBuilder {
    private lateinit var activity: Activity
    private val slotViews: MutableList<SlotView> = mutableListOf()
    private val drawableIds: MutableList<Int> = mutableListOf()
    private var isWork: Boolean = false
    private var scrollTimePerInch: Float = 2f
    private var dockingTimePerInch: Float = 0f //изменить скорость
    private var scrollTime: Int = 3000
    private var childIncTime: Int = 2500
    private lateinit var callback: Callback
    private val layoutManagers: MutableList<LinearLayoutManager> = mutableListOf()

    private fun addSlots(vararg slotsViewId: Int) {
        slotsViewId.forEach { slotId ->
            slotViews.add(activity.findViewById(slotId))
        }
    }

    private fun addDrawables(vararg drawableIds: Int) {
        this.drawableIds.addAll(drawableIds.toList())
        val TAG = drawableIds.size.toString()
        Log.d(TAG,"отработал addDrawables в слотсБилдере")
    }

    private fun build() {
        var timePerInch = scrollTimePerInch
        slotViews.forEach { slotView ->
            SpeedManager.setScrollTime(timePerInch)
            val mLayoutManager = SpeedManager(activity)
            slotView.layoutManager = mLayoutManager
            layoutManagers.add(mLayoutManager as LinearLayoutManager)
            val mAdapter = SlotAdapter(drawableIds.shuffled())
            slotView.adapter = mAdapter
            timePerInch += dockingTimePerInch
        }
        callback.setLayoutManagers(layoutManagers)
        Log.d("${callback.getLayoutManagers().size}", "в слотсбилдере перед передачей в колбек - отработало правильно")
        slotViews.last().addOnScrollListener(ScrollListener(callback))
        drawableIds.clear()

    }

   suspend fun start(): Boolean {
        if (!isWork) {
            isWork = true
            var tempTime = scrollTime
            slotViews.forEach { slotView ->
                tempTime += childIncTime
                val layoutManager = (slotView.layoutManager as LinearLayoutManager)
                slotView.smoothScrollToPosition(layoutManager.findLastVisibleItemPosition() + 100)
                val handler = Handler()
                val runnable = Runnable {
//                    val layoutManager1 = (slotView.layoutManager as LinearLayoutManager) //где-то тут должна быть ошибка
                    val vs = layoutManager.findLastVisibleItemPosition() + 3
                    slotView.smoothScrollToPosition(vs)
                    isWork = false
                }
                handler.postDelayed(runnable, tempTime.toLong())
            }
            return true
        } else {
            Log.e("Slots", "Slots are already run")
            return false
        }
    }

    inner class Builder(private val activity: Activity) {
        init {
            this@SlotsBuilder.activity = activity
        }

        fun addSlots(vararg slotsViewId: Int): Builder {
            this@SlotsBuilder.addSlots(*slotsViewId)
            return this
        }

        fun addDrawables(vararg drawableIds: Int): Builder {
            this@SlotsBuilder.addDrawables(*drawableIds)
            return this
        }

        fun setScrollTimePerInch(scrollTimePerInch: Float): Builder {
            this@SlotsBuilder.scrollTimePerInch = scrollTimePerInch
            return this
        }

        fun setDockingTimePerInch(dockingTimePerInch: Float): Builder {
            this@SlotsBuilder.dockingTimePerInch = dockingTimePerInch
            return this
        }

        fun setScrollTime(scrollTime: Int): Builder {
            this@SlotsBuilder.scrollTime = scrollTime
            return this
        }

        fun setChildIncTime(childIncTime: Int): Builder {
            this@SlotsBuilder.childIncTime = childIncTime
            return this
        }

        fun setOnFinishListener(callback: Callback): Builder {
            this@SlotsBuilder.callback = callback
            return this
        }

        fun build(): SlotsBuilder {
            this@SlotsBuilder.build()
            return this@SlotsBuilder
        }

    }
}
