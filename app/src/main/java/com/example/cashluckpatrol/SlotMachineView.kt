package com.example.cashluckpatrol

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import android.widget.ImageView

class SlotMachineView private constructor (context : Context, attributeSet: AttributeSet) : GridLayout(context) {
 public constructor(context : Context, attributeSet: AttributeSet, rawsNumb : Int, columnNumb : Int,
     images : List<ImageView>) : this(context, attributeSet) {



     }

    init {
        columnCount = rawsNunb




    }

    override fun setColumnCount(columnCount: Int) {
        super.setColumnCount(columnCount)
    }

    override fun setRowCount(rowCount: Int) {
        super.setRowCount(rowCount)
    }
}