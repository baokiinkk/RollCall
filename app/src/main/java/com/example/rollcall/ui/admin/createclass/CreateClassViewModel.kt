package com.example.rollcall.ui.admin.createclass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateClassViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val classes: MutableLiveData<Class?> = MutableLiveData(null)
    val student:MutableLiveData<Users?> = MutableLiveData(null)
    val teacher:MutableLiveData<Users?> = MutableLiveData(null)
    fun getUsers(token:String){
        viewModelScope.launch(Dispatchers.IO){
            teacher.postValue(repo.getTeacher(token))
            student.postValue(repo.getStudent(token))
        }
    }

    fun dataBuoi(): MutableList<String> {
        return mutableListOf("sáng", "chiều")
    }

    fun dataDateLearn(): MutableList<String> {
        return mutableListOf("thứ 2", "thứ 3", "thứ 4", "thứ 5", "thứ 6", "thứ 7")
    }
}