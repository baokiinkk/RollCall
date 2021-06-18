package com.example.rollcall.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.respository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(private val repo:Repository):ViewModel() {
    fun getData(){
        viewModelScope.launch {
            repo.getData {
                Log.d("quocbao","viewmodel+${it.data.results.toString()}")
            }
        }
    }
}