package com.example.rollcall.ui.admin.createuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.R
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateUserViewModel@Inject constructor(private val repo: AdminRepository) :ViewModel(){
    var id:String = ""
    var name:String = ""
    var email:String = ""
    var password:String = ""
    val users: MutableLiveData<Users?> = MutableLiveData(null)
    val img = R.drawable.edit

    fun createUser(token:String,typeUser:String){
        viewModelScope.launch(Dispatchers.IO){
            delay(1000)
            val user = User(id,name,email,typeUser,password=password)
            users.postValue(repo.createUser(token,user))
        }
    }
}