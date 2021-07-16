package com.example.rollcall.ui.admin.editclass

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.R
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.admin.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditClassViewModel @Inject constructor(private val repo: AdminRepository) : ViewModel() {
    val classes: MutableLiveData<Class?> = MutableLiveData(null)
    val teacher: MutableLiveData<Users?> = MutableLiveData(null)
    val datastudent: MutableLiveData<Users?> = MutableLiveData(null)
    val isDelete: MutableLiveData<Class?> = MutableLiveData(null)
    var id: String = ""
    var name: String = ""
    var room: String = ""
    var days: String = ""
    var dateStart: String = ""
    var credit: String = ""
    var dayOfWeek: String = ""
    var nameTeacher = ""
    var shift: String = ""
    val img = R.drawable.edit
    lateinit var student: MutableList<User>
    lateinit var datateacher: User

    fun getData(dataClass: DataClass?, token: String?) {
        dataClass?.let { it ->
            id = it.id
            name = it.name
            room = it.room.toString()
            days = it.days.toString()
            dateStart = it.dateStart.toString()
            credit = it.credit.toString()
            dayOfWeek = "Thứ ${it.dayOfWeek}"
            shift = if (shift == "0") "Sáng" else "Chiều"
            it.students?.let {
                student = it
            }
            it.teacher?.let { teacher ->
                nameTeacher = teacher.name
                datateacher = teacher
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            teacher.postValue(token?.let { repo.getTeacher(it) })
            datastudent.postValue(token?.let { repo.getStudent(it) })
        }
    }

    fun editClass(token: String) {
        val newData = DataClass(
            id,
            name,
            datateacher,
            room,
            student,
            dayOfWeek = dayOfWeek.substring(4),
            shift = if (shift == "Sáng") "0" else "1",
            credit = credit.toInt(),
            days = days.toInt(),
            dateStart = dateStart
        )
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            classes.postValue(repo.editClass(token, newData))
        }
    }

    fun deleteClass(token: String?, dataClass: DataClass?) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            isDelete.postValue(token?.let {
                dataClass?.let { dataClass ->
                    repo.deleteClass(it, dataClass)
                }
            })
        }
    }

    fun dataBuoi(): List<String> {
        return listOf("sáng", "chiều")
    }

    fun dataDateLearn(): MutableList<String> {
        return mutableListOf("thứ 2", "thứ 3", "thứ 4", "thứ 5", "thứ 6", "thứ 7")
    }
}