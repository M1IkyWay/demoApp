package com.example.cashluckpatrol

import androidx.recyclerview.widget.LinearLayoutManager

abstract class Callback {
    abstract fun onFinishListener()

    private var layoutManagers: List<LinearLayoutManager>? = null

    fun setLayoutManagers(layoutManagers: List<LinearLayoutManager>) {
        this.layoutManagers = layoutManagers
    }

    fun getLayoutManagers(): List<LinearLayoutManager>? {
        return layoutManagers
    }

}