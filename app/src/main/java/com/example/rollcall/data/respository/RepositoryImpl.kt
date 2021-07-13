package com.example.rollcall.data.respository

import android.util.Log
import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.DashBoard
import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.Users
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val apiService: ApiService) : Repository {

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
}

