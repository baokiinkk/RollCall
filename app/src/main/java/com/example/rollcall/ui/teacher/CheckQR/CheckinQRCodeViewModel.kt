package com.example.rollcall.ui.teacher.CheckQR

import androidx.lifecycle.ViewModel
import com.example.rollcall.data.respository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CheckinQRCodeViewModel @Inject constructor(private val repo: Repository): ViewModel() {
}