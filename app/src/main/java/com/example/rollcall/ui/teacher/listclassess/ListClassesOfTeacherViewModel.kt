package com.example.rollcall.ui.teacher.listclassess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.respository.Repository
import com.example.rollcall.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListClassesOfTeacherViewModel @Inject constructor(private val repo: Repository): ViewModel(){

}