package com.example.rollcall.binding

import androidx.databinding.BindingAdapter

import androidx.viewpager2.widget.ViewPager2
import com.example.rollcall.R
import com.example.rollcall.adapter.ViewPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerBinding {
    companion object {

        @BindingAdapter("android:tab_layout", "android:adapter_viewpager")
        @JvmStatic
        fun viewpager2(view: ViewPager2, tabLayout: TabLayout, adapter: ViewPageAdapter) {
            view.adapter = adapter
            TabLayoutMediator(
                tabLayout,
                view
            ) { tab, pos ->
                if(adapter.itemCount>2){
                    when (pos) {
                        0->{
                            tab.text = "Thông tin"
                            tab.setIcon(R.drawable.ic_nav_dash_board)
                        }
                        1 -> {
                            tab.text = "DS lớp"
                            tab.setIcon(R.drawable.ic_nav_list_class)
                        }
                        2 -> {
                            tab.text = "Cá Nhân"
                            tab.setIcon(R.drawable.ic_nav_person_24)
                        }
                    }
                }
                else{
                    when (pos) {
                        0 -> {
                            tab.text = "DS lớp"
                            tab.setIcon(R.drawable.ic_nav_list_class)
                        }
                        1 -> {
                            tab.text = "Cá Nhân"
                            tab.setIcon(R.drawable.ic_nav_person_24)
                        }
                    }
                }

            }.attach()
        }

    }
}