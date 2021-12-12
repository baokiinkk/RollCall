package com.example.rollcall.ui.user.CheckQR

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Message
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.UserId
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CheckinQRCodeViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {

    val result: MutableLiveData<Message?> = MutableLiveData(null)

    fun checkin(token: String, id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(
                id?.let { repo.checkin(token, it) })
        }
    }

    fun checkinByTeacher(token: String, id: String?, userId: UserId) {
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(
                id?.let { repo.checkinByTeacher(token, it, userId) })
        }
    }
}