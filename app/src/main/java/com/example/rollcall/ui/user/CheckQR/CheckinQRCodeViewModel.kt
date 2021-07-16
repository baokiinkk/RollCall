package com.example.rollcall.ui.user.CheckQR

import androidx.lifecycle.ViewModel
import com.example.rollcall.data.respository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CheckinQRCodeViewModel @Inject constructor(private val repo: UserRepository): ViewModel() {
}