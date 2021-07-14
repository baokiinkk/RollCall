package com.example.rollcall.binding

import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rollcall.adapter.ItemClassAdapter
import com.example.rollcall.adapter.ItemUserAdapter
import com.example.rollcall.adapter.SelectItemUserAdapter
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users

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
        fun loadRecycleV(view: RecyclerView,itemUserAdapter: SelectItemUserAdapter) {
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
        @BindingAdapter("android:adapter", "android:list")
        @JvmStatic
        fun editChange(
            view: SearchView,
            adapter: ItemUserAdapter,
            list: MutableLiveData<Users?>
        ) {
            view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    list.value?.let {
                        it.data?.let { it1 -> adapter.filter(newText, it1) }
                    }

                    return false
                }

            })
        }

        @BindingAdapter("android:adapter", "android:list")
        @JvmStatic
        fun editChanges(
            view: SearchView,
            adapter: SelectItemUserAdapter,
            list: MutableLiveData<Users?>
        ) {
            view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    list.value?.let {
                        it.data?.let { it1 -> adapter.filter(newText, it1) }
                    }

                    return false
                }

            })
        }

        @BindingAdapter("android:adapter", "android:list")
        @JvmStatic
        fun editChangeClass(
            view: SearchView,
            adapter: ItemClassAdapter,
            list: MutableLiveData<Class?>
        ) {
            view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    list.value?.let {
                        it.data?.let { it1 -> adapter.filter(newText, it1) }
                    }
                    return false
                }

            })
        }
    }
}