package com.example.rollcall.binding

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Spinner

import androidx.databinding.BindingAdapter

import coil.load
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
        fun loadImageURL(view: TextInputLayout, data: List<String>) {
            val adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_item,data)
            (view.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        }
    }
}