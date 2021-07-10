package com.example.rollcall.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.adapter.ItemHomeAdapter

class RecycleViewBinding {
    companion object {
        @BindingAdapter("android:adapter")
        @JvmStatic
        fun loadRecycle(view: RecyclerView,itemHomeAdapter: ItemHomeAdapter) {
            view.apply {
                layoutManager = GridLayoutManager(view.context,1)
                adapter = itemHomeAdapter
            }
        }
    }
}