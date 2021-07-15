package com.example.rollcall.ui.admin.createclass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateClassViewModel @Inject constructor(private val repo: AdminRepository) : ViewModel() {
    val classes: MutableLiveData<Class?> = MutableLiveData(null)
    val student: MutableLiveData<Users?> = MutableLiveData(null)
    val teacher: MutableLiveData<Users?> = MutableLiveData(null)
    var id: String = ""
    var name: String = ""
    var room: String = ""
    var days: String = ""
    var nameTeacher = ""
    var dateStart: String = ""
    var credit: String = ""
    lateinit var datateacher: User
    fun getUsers(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            teacher.postValue(repo.getTeacher(token))
            student.postValue(repo.getStudent(token))
        }
    }

    fun createClass(buoiHoc: String, ngayHoc: String): DataClass {
        return DataClass(
            id,
            name,
            datateacher,
            room,
            dayOfWeek = ngayHoc,
            shift = buoiHoc,
            credit = credit.toInt(),
            days = days.toInt(),
            dateStart = dateStart
        )
    }

    fun dataBuoi(): List<String> {
        return listOf("sáng", "chiều")
    }

    fun dataDateLearn(): MutableList<String> {
        return mutableListOf("thứ 2", "thứ 3", "thứ 4", "thứ 5", "thứ 6", "thứ 7")
    }
}