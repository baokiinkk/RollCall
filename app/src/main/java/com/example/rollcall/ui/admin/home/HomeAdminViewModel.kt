package com.example.rollcall.ui.admin.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.ItemHomeModel
import com.example.rollcall.data.respository.Repository
import com.example.rollcall.utils.Utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAdminViewModel@Inject constructor(private val repo: Repository):ViewModel() {
    fun getData():MutableList<ItemHomeModel>{
        val list = mutableListOf<ItemHomeModel>()
        list.add(ItemHomeModel("Số lượng sinh viên","5 học sinh"))
        list.add(ItemHomeModel("Số lượng Lớp","5 Lớp"))
        list.add(ItemHomeModel("Số lượng Giảng viên","5 giảng viên"))
        return list
    }
}