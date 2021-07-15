package com.example.rollcall.ui.admin.ediuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditUserViewModel@Inject constructor(private val repo: AdminRepository) :ViewModel(){
    var id:String = ""
    var name:String = ""
    var email:String = ""
    var password:String = ""
    val users: MutableLiveData<Users?> = MutableLiveData(null)
    val isDelete: MutableLiveData<Users?> = MutableLiveData(null)

    fun editUser(token: String){
        val user = User(id,name,email,password = password)
        viewModelScope.launch(Dispatchers.IO){
            delay(1000)
            val tmp = repo.editUser(token,user)
            if(tmp.message==null && password != ""){
                users.postValue(repo.editPasswordUser(token,user))
            }
            else
                users.postValue(tmp)
        }
    }
    fun deleteUser(token: String){
        viewModelScope.launch(Dispatchers.IO){
            delay(1000)
            val user = User(id,name,email,password)
            isDelete.postValue(repo.deleteUser(token,user))
        }
    }
}