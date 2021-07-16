package com.example.rollcall.ui.user.home

import androidx.lifecycle.ViewModel
import com.example.rollcall.R
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeUserViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {
    val img = R.drawable.home
}