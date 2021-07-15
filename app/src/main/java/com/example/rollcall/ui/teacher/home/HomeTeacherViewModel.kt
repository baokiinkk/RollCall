package com.example.rollcall.ui.teacher.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.DashBoard
import com.example.rollcall.data.respository.admin.AdminRepository
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTeacherViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {

}