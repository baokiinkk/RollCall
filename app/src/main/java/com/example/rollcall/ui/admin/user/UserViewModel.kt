package com.example.rollcall.ui.admin.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.Repository
import com.example.rollcall.utils.Utils.STUDENT
import com.example.rollcall.utils.Utils.TEACHER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel@Inject constructor(private val repo: Repository):ViewModel() {
    val users:MutableLiveData<Users?> = MutableLiveData(null)
    val classes:MutableLiveData<Class?> = MutableLiveData(null)
    fun getUsers(token:String,user:String){
        viewModelScope.launch(Dispatchers.IO){
            when (user) {
                STUDENT -> users.postValue(repo.getStudent(token))
                TEACHER -> users.postValue(repo.getTeacher(token))
            }
        }
    }
}