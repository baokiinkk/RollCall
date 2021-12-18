package com.example.rollcall.ui.admin.createuser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.R
import com.example.rollcall.data.model.*
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateUserViewModel@Inject constructor(private val repo: AdminRepository) :ViewModel(){
    var id:String = ""
    var firstName:String = ""
    var lastName:String = ""
    var email:String = ""
    var password:String = ""
    var major:String = ""
    val users: MutableLiveData<Boolean?> = MutableLiveData(null)
    val img = R.drawable.background
    val majorData:MutableLiveData<List<String>> = MutableLiveData(mutableListOf(""))

    fun createUser(token:String,typeUser:String){
        viewModelScope.launch(Dispatchers.IO){
            delay(1000)
            val user = UserPost(id,firstName,lastName,email = email,role= typeUser,password = password,majorId = major)
            users.postValue(repo.createUser(token,user))
        }
    }
    fun getMajor(token: String){
        viewModelScope.launch(Dispatchers.IO){
            Log.d("quocbao",repo.getMajors(token).data.toString())
            majorData.postValue(repo.getMajors(token).data?.map {
                it.majorId
            })
        }
    }
}