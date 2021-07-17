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

    @GET("users/{id}")
    suspend fun getInfoUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
    ): Users

    //----------------------------------- Class ----------------------------------------------------
    @GET("classes/")
    suspend fun getClass(@Header("Authorization") tokenAdmin: String): Class

    @POST("classes/")
    suspend fun createClass(
        @Header("Authorization") tokenAdmin: String,
        @Body classes: DataClass
    ): Class

    @PUT("classes/")
    suspend fun editClass(
        @Header("Authorization") tokenAdmin: String,
        @Body dataClass: DataClass
    ):Class

    @DELETE("classes/{id}")
    suspend fun deleteClass(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String
    ): Class

    //----------------------------------- Teacher --------------------------------------------------
    @GET("teachers/{id}/class")
    suspend fun getClassOfTeacher(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
    ): Class
}