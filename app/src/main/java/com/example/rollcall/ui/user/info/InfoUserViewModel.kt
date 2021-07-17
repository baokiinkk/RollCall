package com.example.rollcall.ui.user.info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class InfoUserViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    val users: MutableLiveData<Users?> = MutableLiveData(null)
    val isLogOut:MutableLiveData<Boolean?> = MutableLiveData(null)
    var token = ""
    fun getInfoUser(token: String?, id: String?) {
        token?.let { this.token = it }
        viewModelScope.launch(Dispatchers.IO) {
            users.postValue(
                token?.let {token->
                    id?.let { id->
                        repo.getInfoUser(token, id)
                    }

            })
        }
    }
    fun logout(){
        viewModelScope.launch(Dispatchers.IO){
            if(repo.logOut(token).message == "Success")
                isLogOut.postValue(true)
        }
    }
}