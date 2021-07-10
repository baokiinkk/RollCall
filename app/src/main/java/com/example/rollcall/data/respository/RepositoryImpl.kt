package com.example.rollcall.data.respository

import com.example.rollcall.data.api.ApiService
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

    override suspend fun getUses(tokenAdmin: String): Users =
        try {
            apiService.getUser(tokenAdmin)
        }catch (cause:HttpException){
            Users(message = cause.response()?.errorBody()?.string())
        }


}

