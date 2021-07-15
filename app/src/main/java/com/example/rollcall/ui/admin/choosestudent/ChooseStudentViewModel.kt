package com.example.rollcall.ui.admin.choosestudent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChooseStudentViewModel@Inject constructor(private val repo: AdminRepository):ViewModel() {
    val users:MutableLiveData<Users?> = MutableLiveData(null)
    val classes: MutableLiveData<Class?> = MutableLiveData(null)

    fun getUsers(token:String){
        viewModelScope.launch(Dispatchers.IO){
            users.postValue(repo.getStudent(token))
        }
    }

    fun createClass(token: String, data:DataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            classes.postValue(repo.createClass(token, data))
        }
    }
}