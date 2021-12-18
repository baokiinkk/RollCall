package com.example.rollcall.data.respository.admin

import android.util.Log
import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.*
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    AdminRepository {

    //------------------------ Users --------------------------------------
    override suspend fun login(login: LoginUser): Users =
        try {
            apiService.login(login)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)
        }

    override suspend fun getStudent(tokenAdmin: String): Users =
        try {
            apiService.getStudents(tokenAdmin)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)
        }

    override suspend fun getTeacher(tokenAdmin: String): Users =
        try {
            apiService.getTeachers(tokenAdmin)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)
        }

    override suspend fun getDashBoard(tokenAdmin: String): DashBoard =
        try {
            apiService.getDashBoard(tokenAdmin)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), DashBoard::class.java)
        }

    override suspend fun createUser(tokenAdmin: String, users: UserPost): Boolean {
        val response = apiService.createUser(tokenAdmin, users)
        return response.isSuccessful
    }
    override suspend fun editUser(tokenAdmin: String, user: UserPost): Users =
        try {
            apiService.editUser(tokenAdmin, user.userId, user)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)
        }

    override suspend fun editPasswordUser(tokenAdmin: String, user: UserPost): Users =
        try {
            apiService.editPasswordUser(tokenAdmin, user.userId, user)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)
        }

    override suspend fun deleteUser(tokenAdmin: String, user: UserPost): Users =
        try {
            apiService.deleteUser(tokenAdmin, user.userId)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)

        }

    override suspend fun logOut(token: String): Users =
        try {
            apiService.logout(token)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Users::class.java)

        }

    override suspend fun getMajors(tokenAdmin: String): MajorReponse=
        try {
            apiService.getMajors(tokenAdmin)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), MajorReponse::class.java)
        }


    //------------------------ Class --------------------------------------

    override suspend fun getClass(tokenAdmin: String): Class =
        try {
            apiService.getClass(tokenAdmin)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Class::class.java)

        }

    override suspend fun createClass(tokenAdmin: String, classes: DataClass): Class =
        try {
            apiService.createClass(tokenAdmin, classes)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Class::class.java)
        }

    override suspend fun editClass(tokenAdmin: String, user: DataClass): Class =
        try {
            apiService.editClass(tokenAdmin, user)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(), Class::class.java)
        }

    override suspend fun deleteClass(tokenAdmin: String, user: DataClass): Class =
        try {
            apiService.deleteClass(tokenAdmin, user.id)
        } catch (cause: HttpException) {
            Class(message = "0")

        }
}

