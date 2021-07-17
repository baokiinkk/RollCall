package com.example.rollcall.ui.user.listclassess.classInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.DataClass
import com.example.rollcall.data.model.DataReport
import com.example.rollcall.data.model.Report
import com.example.rollcall.data.model.ReportBody
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassInfoViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    val users: MutableLiveData<DataReport?> = MutableLiveData(null)
    val getUsers: MutableLiveData<DataReport?> = MutableLiveData(null)
    var classinfor:DataClass? = null
    fun getReport(id: String?, token: String?, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            users.postValue(token?.let { token ->
                id?.let { id -> repo.getReportStatus(id, date, token) }
            })
        }
    }

    fun createReport(id: String?,date: ReportBody, token: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            getUsers.postValue(id?.let { id ->
                token?.let { repo.createReport(id, date,it) }
            })
        }
    }
}