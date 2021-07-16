package com.example.rollcall.ui.user.listclassess.classInfo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.*
import com.example.rollcall.data.respository.user.UserRepository
import com.example.rollcall.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassInfoViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    val users: MutableLiveData<DataReport?> = MutableLiveData(null)
    val getUsers: MutableLiveData<DataReport?> = MutableLiveData(null)
    var LinkUrl = MutableLiveData<String?>()
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

    fun downLoadAll(id: String?) {
        LinkUrl.value = Utils.BASE_URL + id + "/download-all"
    }
}