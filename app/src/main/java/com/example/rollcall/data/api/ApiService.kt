package com.example.rollcall.data.api

import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.UserLogin
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body loginUser: LoginUser): UserLogin
}