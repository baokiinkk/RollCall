package com.example.rollcall.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.R
import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(private val repo: AdminRepository):ViewModel() {
    val img = R.drawable.brlogin
    var email = ""
    var password = ""
    var user:MutableLiveData<Users?> = MutableLiveData(null)

    fun login(){
        val loginUser = LoginUser(email,password)
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            user.postValue(repo.login(loginUser))
        }
    }
}