package com.example.cashluckpatrol

import androidx.recyclerview.widget.RecyclerView

class ScrollListener(private val callback: Callback) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        when (newState) {
            RecyclerView.SCROLL_STATE_IDLE -> callback.onFinishListener()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // Оставляем этот метод пустым, если он не используется
    }
}
