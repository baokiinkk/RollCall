package com.example.rollcall.ui.teacher.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class InfoUserViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {
    val users: MutableLiveData<Users?> = MutableLiveData(null)

    fun getInfoUser(token: String?, id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            users.postValue(
                token?.let {token->
                    id?.let { id->
                        repo.getInfoUser(token, id)
                    }

            })
        }
    }
}