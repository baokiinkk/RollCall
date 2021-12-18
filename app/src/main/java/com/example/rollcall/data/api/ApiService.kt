package com.example.rollcall.data.api

import com.example.rollcall.data.model.*
import retrofit2.Response
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
    suspend fun createUser(@Header("Authorization") tokenAdmin: String, @Body user: UserPost): Response<Users>

    @PUT("users/{id}")
    suspend fun editUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
        @Body user: UserPost
    ): Users

    @PUT("users/{id}/password")
    suspend fun editPasswordUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
        @Body user: UserPost
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

    @POST("users/logout")
    suspend fun logout(@Header("Authorization") tokenAdmin: String): Users

    //----------------------------------- Class ----------------------------------------------------
    @GET("subjects/")
    suspend fun getClass(@Header("Authorization") tokenAdmin: String): Class

    @POST("subjects/")
    suspend fun createClass(
        @Header("Authorization") tokenAdmin: String,
        @Body classes: DataClass
    ): Class

    @PUT("subjects/")
    suspend fun editClass(
        @Header("Authorization") tokenAdmin: String,
        @Body dataClass: DataClass
    ): Class

    @DELETE("subjects/{id}")
    suspend fun deleteClass(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String
    ): Class

    //----------------------------------- Teacher --------------------------------------------------
    @GET("{user}/{id}/class")
    suspend fun getClassUser(
        @Header("Authorization") tokenAdmin: String,
        @Path("id") id: String,
        @Path("user") user: String
    ): Class


    //----------------------------------- Report --------------------------------------------------
    @POST("reports/{class_id}")
    suspend fun postReport(
        @Path("class_id") id: String,
        @Body data:ReportBody,
        @Header("Authorization") tokenAdmin: String
    ):DataReport

    @GET("reports/{id}/status")
    suspend fun getReport(
        @Path("id") id: String,
        @Header("Authorization") tokenAdmin: String
    ):DataReport


    @GET("reports/{class_id}/{date}/status")
    suspend fun getReportStatus(
        @Path("class_id") id: String,
        @Path("date")date:String,
        @Header("Authorization") tokenAdmin: String
    ):DataReport
    @GET("majors")
    suspend fun getMajors(
        @Header("Authorization") tokenAdmin: String
    ):MajorReponse

    @POST("reports/{id}/checkin")
    suspend fun checkin(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Message

    @POST("reports/{id}/teachercheckin")
    suspend fun checkinByTeacher(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body userId: UserId
    ): Message

    @GET("reports/{class_id}/download-all")
    suspend fun downLoadReport(
        @Path("class_id") id: String,
    ): Message
}
