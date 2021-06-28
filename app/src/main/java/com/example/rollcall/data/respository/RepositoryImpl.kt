package com.example.rollcall.data.respository

import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.LoginUser
import com.example.rollcall.data.model.User
import com.example.rollcall.data.model.UserLogin
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val apiService: ApiService) : Repository {
    override suspend fun login(login: LoginUser): UserLogin =
        try {
            apiService.login(login)
        } catch (cause: HttpException) {
            UserLogin(message = cause.response()?.errorBody()?.string())
        }


}

