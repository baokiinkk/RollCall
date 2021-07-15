package com.example.rollcall.ui.admin.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.Repository
import com.example.rollcall.utils.Utils.STUDENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel@Inject constructor(private val repo: Repository):ViewModel() {
    val users:MutableLiveData<Users?> = MutableLiveData(null)
    fun getUsers(token:String,user:String){
        viewModelScope.launch(Dispatchers.IO){
            if(user == STUDENT)
                users.postValue(repo.getStudent(token))
            else
                users.postValue(repo.getTeacher(token))
        }
    }
}