package com.example.rollcall.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.R
import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.UserLogin
import com.example.rollcall.data.respository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(private val repo:Repository):ViewModel() {
    val img = R.drawable.brlogin
    var email = ""
    var password = ""
    var user:MutableLiveData<UserLogin?> = MutableLiveData(null)
    fun login(){
        val loginUser = LoginUser(email,password)
        viewModelScope.launch {
            user.postValue(repo.login(loginUser))
        }
    }
}