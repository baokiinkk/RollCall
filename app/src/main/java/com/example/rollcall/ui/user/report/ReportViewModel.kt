package com.example.rollcall.ui.user.report

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.DataReport
import com.example.rollcall.data.model.Report
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    val users: MutableLiveData<DataReport?> = MutableLiveData(null)

    fun getReport(id: String?, token: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                users.postValue(id?.let {id->
                    token?.let { token ->
                        repo.getReport(id, token)
                    }
                })
                delay(2000)
            }

        }
    }
}