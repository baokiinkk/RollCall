package com.example.rollcall.data.api

import com.example.rollcall.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("users/login")
    suspend fun login(@Body loginUser: LoginUser): Users

    @GET("students")
    suspend fun getStudents(@Header("Authorization") tokenAdmin: String): Users

    @GET("teachers")
    suspend fun getTeachers(@Header("Authorization") tokenAdmin: String): Users

    @GET("dashboard")
    suspend fun getDashBoard(@Header("Authorization") tokenAdmin: String): DashBoard

    @POST("users")
    suspend fun createUser(@Header("Authorization") tokenAdmin: String, @Body user: User): Users

    @PUT("users/{id}")
    suspend fun editUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
        @Body user: User
    ): Users

    @PUT("users/{id}/password")
    suspend fun editPasswordUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
        @Body user: User
    ): Users

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String
    ): Users

    @GET("classes/")
    suspend fun getClass(@Header("Authorization") tokenAdmin: String): Class

    @POST("classes/")
    suspend fun createClass(@Header("Authorization") tokenAdmin: String,@Body classes: DataClass): Class
}