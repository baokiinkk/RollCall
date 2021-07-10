package com.example.rollcall.data.api

import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.Users
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body loginUser: LoginUser): Users

    @GET("users")
    suspend fun getUser(@Header("Authorization") tokenAdmin:String):Users
}