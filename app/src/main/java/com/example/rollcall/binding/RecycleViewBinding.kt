package com.example.rollcall.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.adapter.ItemUserAdapter

class RecycleViewBinding {
    companion object {

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun loadRecycle(view: RecyclerView,itemUserAdapter: ItemUserAdapter) {
            view.apply {
                layoutManager = GridLayoutManager(view.context,1)
                adapter = itemUserAdapter
            }
        }

        @BindingAdapter("android:adapter")
        @JvmStatic
        fun loadRecycleView(view: RecyclerView,itemUserAdapter: ItemClassAdapter) {
            view.apply {
                layoutManager = GridLayoutManager(view.context,1)
                adapter = itemUserAdapter
            }
        }
    }
}