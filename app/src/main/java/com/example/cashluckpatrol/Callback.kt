package com.example.cashluckpatrol

import androidx.recyclerview.widget.LinearLayoutManager

abstract class Callback {
    abstract fun onFinishListener()

    private var layoutManagers: List<LinearLayoutManager> = emptyList()

    fun setLayoutManagers(_layoutManagers: List<LinearLayoutManager>) {
        this.layoutManagers = _layoutManagers
    }

    fun getLayoutManagers(): List<LinearLayoutManager> {
        return layoutManagers
    }

}