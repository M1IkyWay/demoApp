package com.example.cashluckpatrol

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DiagonalLayoutManager (context: Context) : LinearLayoutManager (context){

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return 0
    }

    override fun canScrollVertically(): Boolean {
        return false
    }

}