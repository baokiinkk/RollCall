package com.example.rollcall.data.respository

import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.DashBoard
import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.Users
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val apiService: ApiService) : Repository {
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

    override suspend fun getTeachcer(tokenAdmin: String): Users =
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

}

