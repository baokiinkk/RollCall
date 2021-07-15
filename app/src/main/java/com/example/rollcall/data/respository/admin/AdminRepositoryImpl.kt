package com.example.rollcall.data.respository.admin

import android.util.Log
import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.*
import retrofit2.HttpException
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(val apiService: ApiService) : AdminRepository {

    //------------------------ Users --------------------------------------
    override suspend fun login(login: LoginUser): Users =
        try {
            apiService.login(login)
        } catch (cause: HttpException) {
            Users(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun getStudent(tokenAdmin: String): Users =
        try {
            apiService.getStudents(tokenAdmin)
        } catch (cause: HttpException) {
            Users(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun getTeacher(tokenAdmin: String): Users =
        try {
            apiService.getTeachers(tokenAdmin)
        } catch (cause: HttpException) {
            Users(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun getDashBoard(tokenAdmin: String): DashBoard =
        try {
            apiService.getDashBoard(tokenAdmin)
        } catch (cause: HttpException) {
            DashBoard(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun createUser(tokenAdmin: String,users: User): Users =
        try {
            apiService.createUser(tokenAdmin,users)
        } catch (cause: HttpException) {
            Users(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun editUser(tokenAdmin: String, user: User): Users =
        try {
            apiService.editUser(tokenAdmin,user.id,user)
        } catch (cause: HttpException) {
            Users(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun editPasswordUser(tokenAdmin: String, user: User): Users =
        try {
            Log.d("quocbao",user.toString())
            apiService.editPasswordUser(tokenAdmin,user.id,user)
        } catch (cause: HttpException) {
            Users(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun deleteUser(tokenAdmin: String, user: User): Users =
        try {
            apiService.deleteUser(tokenAdmin,user.id)
        } catch (cause: HttpException) {
            Users(message = "0")

        }



    //------------------------ Class --------------------------------------

    override suspend fun getClass(tokenAdmin: String): Class =
        try {
            apiService.getClass(tokenAdmin)
        } catch (cause: HttpException) {
            Class(message = cause.response()?.errorBody()?.string())

        }

    override suspend fun createClass(tokenAdmin: String, classes: DataClass): Class =
    try {
        apiService.createClass(tokenAdmin,classes)
    } catch (cause: HttpException) {
        Class(message = cause.response()?.errorBody()?.string())
    }

    override suspend fun editClass(tokenAdmin: String, user: DataClass): Class =
        try {
            apiService.editClass(tokenAdmin,user)
        } catch (cause: HttpException) {
            Class(message = cause.response()?.errorBody()?.string())
        }

    override suspend fun deleteClass(tokenAdmin: String, user: DataClass): Class =
        try {
            apiService.deleteClass(tokenAdmin,user.id)
        } catch (cause: HttpException) {
            Class(message = "0")

        }
}

