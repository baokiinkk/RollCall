package com.example.rollcall.ui.admin.ediuser

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
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class EditUserViewModel @Inject constructor(private val repo: AdminRepository) : ViewModel() {
    var id:String = ""
    var firstName:String? = ""
    var lastName:String? = ""
    var email:String = ""
    var password:String? = ""
    var majorId:String? = ""
    var birthplace:String? = ""
    var birthDay:String? = ""
    var phone:String? = ""
    var sex:String? = ""
    var datePicker:Date? = null
    val majorData:MutableLiveData<List<String>> = MutableLiveData(mutableListOf(""))
    val users: MutableLiveData<Users?> = MutableLiveData(null)
    val isDelete: MutableLiveData<Users?> = MutableLiveData(null)
    val img = R.drawable.edit
    private val df =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").apply {
        timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
    }
    fun editUser(token: String) {
        val user = UserPost(
            id,
            firstName = firstName,
            lastName =lastName,
            email = email,
            password = password,
            majorId = majorId,
            birthplace = birthplace,
            phone = phone,
            birthDate = df.format(datePicker),
            sex = if(sex == "Nữ") 0 else 1
        )
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val tmp = repo.editUser(token, user)
            if (tmp.message == null && password != "") {
                users.postValue(repo.editPasswordUser(token, user))
            } else
                users.postValue(tmp)
        }
    }

    fun deleteUser(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val user = UserPost(
                id,
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
                majorId = "aaa"
            )
            isDelete.postValue(repo.deleteUser(token, user))
        }
    }
    fun dataGioiTinh(): List<String> {
        return listOf("Nam", "Nữ")
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