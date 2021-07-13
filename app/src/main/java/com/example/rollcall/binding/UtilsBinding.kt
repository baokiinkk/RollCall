package com.example.rollcall.binding

import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner

import androidx.databinding.BindingAdapter

import coil.load

class UtilsBinding{
    companion object {
        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImage(view: ImageView, image: Int?) {
            image?.let {
                view.load(it)
            }
        }
        @BindingAdapter("android:loadImage")
        @JvmStatic
        fun loadImageURL(view: ImageView, image: String?) {
            image?.let {
                view.load(it)
            }
        }

        @BindingAdapter("android:load_data")
        @JvmStatic
        fun loadImageURL(view: Spinner, data: MutableList<String>) {
            view.adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,data)
        }
    }
}