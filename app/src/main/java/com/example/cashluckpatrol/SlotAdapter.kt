package com.example.cashluckpatrol

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class SlotAdapter(val myDataset: List<Int>) : RecyclerView.Adapter<SlotAdapter.ViewHolder>() {


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mImageView: ImageView = v.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataIndex = myDataset[position % myDataset.size]
        val TAG = "${myDataset.size}"
        Log.d(TAG, "количество элементов в датасете в слотАдаптере")
        holder.mImageView.setImageResource(dataIndex)
        holder.mImageView.tag = dataIndex
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}
