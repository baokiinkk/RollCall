package com.example.rollcall.data.respository.user

import com.example.rollcall.data.api.ApiService
import com.example.rollcall.data.model.Class
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService) : UserRepository{
    override suspend fun getClassOfTeacher(token: String, id: String, user: String): Class =
            try {
                apiService.getClassOfTeacher(token, id, user)
            } catch (cause: HttpException) {
                Gson().fromJson(cause.response()?.errorBody()?.string(),Class::class.java)

            }
}