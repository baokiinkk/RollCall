package com.example.rollcall.ui.user.listclassess

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ListClassesViewModel @Inject constructor(private val repo: UserRepository): ViewModel(){
    val classes: MutableLiveData<Class?> = MutableLiveData(null)
    fun getListClass(token:String, id: String, user: String){
        viewModelScope.launch(Dispatchers.IO){
            Log.d("result", repo.getClassUser(token, id, user).toString())
            classes.postValue(repo.getClassUser(token, id, user))
        }
    }
}