package com.example.rollcall.ui.admin.classes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClassViewModel@Inject constructor(private val repo: AdminRepository):ViewModel() {
    val classes:MutableLiveData<Class?> = MutableLiveData(null)
    fun getUsers(token:String){
        viewModelScope.launch(Dispatchers.IO){
            classes.postValue(repo.getClass(token))
        }
    }
}