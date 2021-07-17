package com.example.rollcall.data.respository.user

import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.Class
import com.example.rollcall.data.model.Users
import com.example.rollcall.data.respository.user.UserRepository
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository{
    override suspend fun getClassOfTeacher(tokenTeacher: String, id: String): Class =
            try {
                apiService.getClassOfTeacher(tokenTeacher,id)
            } catch (cause: HttpException) {
                Gson().fromJson(cause.response()?.errorBody()?.string(),Class::class.java)

            }

    override suspend fun getInfoUser(token: String, id: String): Users =
        try {
            apiService.getInfoUser(token,id)
        } catch (cause: HttpException) {
            Gson().fromJson(cause.response()?.errorBody()?.string(),Users::class.java)

        }
}