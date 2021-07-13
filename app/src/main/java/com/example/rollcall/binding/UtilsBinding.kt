package com.example.rollcall.binding

import android.widget.ImageView

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
    }
}