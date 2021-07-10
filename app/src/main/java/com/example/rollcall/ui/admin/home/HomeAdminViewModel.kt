package com.example.rollcall.ui.admin.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.DashBoard
import com.example.rollcall.data.model.ItemHomeModel
import com.example.rollcall.data.respository.Repository
import com.example.rollcall.utils.Utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAdminViewModel@Inject constructor(private val repo: Repository):ViewModel() {
    val dashBoard:MutableLiveData<DashBoard?> = MutableLiveData(null)
    fun getData(token:String){
       viewModelScope.launch(Dispatchers.IO){
           dashBoard.postValue(repo.getDashBoard(token))
       }
    }
}