package com.example.rollcall.data.respository.user

import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.*
import com.example.rollcall.data.respository.user.UserRepository
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository {
    override suspend fun getClassOfTeacher(token: String, id: String, user: String): Class =
        try {
            apiService.getClassOfTeacher(token, id, user)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Class::class.java)

        }

    override suspend fun getInfoUser(token: String, id: String): Users =
        try {
            apiService.getInfoUser(token, id)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)

        }

    override suspend fun logOut(token: String): Users =
        try {
            apiService.logout(token)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)

        }

    override suspend fun createReport(id: String,data: ReportBody, token: String): DataReport =
        try {
            apiService.postReport(id,data, token)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), DataReport::class.java)

        }

    override suspend fun getReportStatus(id: String, date: String, token: String): DataReport =
        try {
            apiService.getReportStatus(id, date, token)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), DataReport::class.java)
        }

    override suspend fun getReport(id: String, token: String): DataReport =
        try {
            apiService.getReport(id, token)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), DataReport::class.java)

        }
}