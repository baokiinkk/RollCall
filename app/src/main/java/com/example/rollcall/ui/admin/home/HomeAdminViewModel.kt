package com.example.rollcall.ui.admin.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.R
import com.example.rollcall.data.model.DashBoard
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAdminViewModel@Inject constructor(private val repo: AdminRepository):ViewModel() {
    val dashBoard:MutableLiveData<DashBoard?> = MutableLiveData(null)
    val img = R.drawable.home
    fun getData(token:String){
       viewModelScope.launch(Dispatchers.IO){
           dashBoard.postValue(repo.getDashBoard(token))
           
       }
    }
}