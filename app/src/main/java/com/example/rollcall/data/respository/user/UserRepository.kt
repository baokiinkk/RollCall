package com.example.rollcall.data.respository.user

import com.example.rollcall.data.model.*

import javax.inject.Singleton


@Singleton
interface UserRepository {
    suspend fun getClassOfTeacher(
        tokenAdmin: String,
        id: String,
        user: String,
    ): Class

    suspend fun getInfoUser(
        token: String,
        id: String,
    ): Users

    suspend fun logOut(token: String): Users
    suspend fun createReport(id:String,data:ReportBody, token: String): DataReport
    suspend fun getReportStatus(id:String,date:String,token: String):DataReport
    suspend fun getReport(id: String,token: String):DataReport

}